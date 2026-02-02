package com.svalero.asociation.service;


import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.repository.ParticipanteRepository;
import org.hibernate.query.ParameterLabelException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ParticipanteService.class);


    public List<Participante> findAll(LocalDate birthDate, String name, String typeRel){
        List<Participante> participantes = participanteRepository.findByFilters(birthDate, name, typeRel);
        logger.info("Searching with filters: {} {} {}", birthDate, name, typeRel);
        return participantes;
    }

    public Participante findById(long id) {
        Participante participanteSelected = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        logger.debug("Fetching participante with ID: {}", id);
        return participanteSelected;
    }

    public  Participante add(Participante participante){
        if(participanteRepository.existsBydni(participante.getDni())){
            throw new BusinessRuleException("Un participante con DNI "+participante.getDni()+" ya existe");
        }
        participanteRepository.save(participante);
        logger.info("Successfully created new participante with ID: {}", participante.getId());
        return participante;
    }

    public Participante modify(long id, Participante participante){
        Participante oldparticipante = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        logger.info("Updating participante with ID: {}", id);
        modelMapper.map(participante, oldparticipante);
        return participanteRepository.save(oldparticipante);
    }

    public void delete(long id) {
        Participante participante = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        logger.info("Participante with ID: {} deleted successfully", id);
        participanteRepository.delete(participante);
    }
}
