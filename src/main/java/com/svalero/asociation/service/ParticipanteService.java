package com.svalero.asociation.service;

import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.repository.ParticipanteRepository;
import org.hibernate.query.ParameterLabelException;
import org.modelmapper.ModelMapper;
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

    public List<Participante> findAll(LocalDate birthDate, String name, String typeRel){
        if(birthDate!= null){
            return participanteRepository.findByBirthDateAfter(birthDate);
        }
        if(name!=null){
            return participanteRepository.findByNameStartingWithIgnoreCase(name);
        }
        if(typeRel!= null) {
            return participanteRepository.findByTypeRel(typeRel);
        }
        return participanteRepository.findAll();
    }

    public Participante findById(long id) {
        Participante foundparticipante = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        return foundparticipante;
    }

    public Participante add(Participante participante){
        if(participanteRepository.existsBydni(participante.getDni())){
            throw new BusinessRuleException("Un socio con DNI "+participante.getDni()+" ya existe");
        }
        participanteRepository.save(participante);
        return participante;
    }

    public Participante modify(long id, Participante participante){
        Participante oldparticipante = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        modelMapper.map(participante, oldparticipante);
        return participanteRepository.save(oldparticipante);
    }

    public void delete(long id) {
        Participante participante = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        participanteRepository.delete(participante);
    }
}
