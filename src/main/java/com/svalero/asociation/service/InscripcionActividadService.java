package com.svalero.asociation.service;

import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.exception.ActividadNotFoundException;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.ParticipanteNotFoundException;
import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.model.InscripcionActividad;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.repository.ActividadRepository;
import com.svalero.asociation.repository.InscripcionActividadRepository;
import com.svalero.asociation.repository.ParticipanteRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscripcionActividadService {

    @Autowired
    private InscripcionActividadRepository inscripcionActividadRepository;
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(InscripcionActividadService.class);

    @Transactional
    public void inscribir(long actividadId, long participanteId, String state, float price) {
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new ActividadNotFoundException("Actividad con ID:" + actividadId + " no encontrada"));
        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ParticipanteNotFoundException("Participante con ID:" + participanteId + " no encontrado"));

        if (!Boolean.TRUE.equals(actividad.getCanJoin())) {
            throw new BusinessRuleException("La actividad con ID " + actividadId + " no admite nuevas inscripciones");
        }
        if (inscripcionActividadRepository.existsByActividadIdAndParticipanteId(actividadId, participanteId)) {
            throw new BusinessRuleException("El participante con ID " + participanteId + " ya esta inscrito en la actividad " + actividadId);
        }
        if (actividad.getCapacity() != null) {
            int inscritos = inscripcionActividadRepository.findByActividadId(actividadId).size();
            if (inscritos >= actividad.getCapacity()) {
                throw new BusinessRuleException("La actividad con ID " + actividadId + " ha alcanzado su capacidad maxima");
            }
        }

        InscripcionActividad inscripcionActividad = new InscripcionActividad();
        inscripcionActividad.setActividad(actividad);
        inscripcionActividad.setParticipante(participante);
        if (state != null && !state.isBlank()) {
            inscripcionActividad.setState(state);
        }
        inscripcionActividad.setPrice(price);
        inscripcionActividadRepository.save(inscripcionActividad);
        logger.info("Participant ID {} enrolled in actividad ID {}", participanteId, actividadId);
    }

    @Transactional
    public void desinscribir(long actividadId, long participanteId) {
        if (!inscripcionActividadRepository.existsByActividadIdAndParticipanteId(actividadId, participanteId)) {
            throw new BusinessRuleException("No existe inscripcion para actividad ID " + actividadId + " y participante ID " + participanteId);
        }
        inscripcionActividadRepository.deleteByActividadIdAndParticipanteId(actividadId, participanteId);
        logger.info("Participant ID {} unenrolled from actividad ID {}", participanteId, actividadId);
    }

    @Transactional(readOnly = true)
    public List<ParticipanteDto> listarParticipantes(long actividadId) {
        if (!actividadRepository.existsById(actividadId)) {
            throw new ActividadNotFoundException("Actividad con ID:" + actividadId + " no encontrada");
        }
        return inscripcionActividadRepository.findByActividadId(actividadId).stream()
                .map(InscripcionActividad::getParticipante)
                .map(participante -> modelMapper.map(participante, ParticipanteDto.class))
                .toList();
    }
}
