package com.svalero.asociation.service;


import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.repository.ParticipanteRepository;
import com.svalero.asociation.repository.SocioRepository;
import org.hibernate.query.ParameterLabelException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    @Autowired
    private  SocioRepository socioRepository;

    public List<Participante> findAll(LocalDate birthDate, String name, String typeRel){
        List<Participante>participantes;
        if(birthDate!= null){
            participantes = participanteRepository.findByBirthDateAfter(birthDate);
        }
        else if(name!=null && !name.isEmpty()){
            participantes = participanteRepository.findByNameStartingWithIgnoreCase(name);
        }
        else if(typeRel!= null && !typeRel.isEmpty()) {
            participantes = participanteRepository.findByTypeRel(typeRel);
        }
        else {
            participantes = participanteRepository.findAll();
        }
        return participantes;
    }

    public Participante findById(long id) {
        Participante participanteSelected = participanteRepository.findById(id).orElseThrow(() -> new ParameterLabelException("Participante con ID:" + id + "no encontrado"));
        return participanteSelected;
    }

    public  Participante add(Participante participante){
        if(participanteRepository.existsBydni(participante.getDni())){
            throw new BusinessRuleException("Un socio con DNI "+participante.getDni()+" ya existe");
        }
        participante.setSocio(socioRepository.findById(socioDto.getId()).orElseThrow(() -> new ParameterLabelException("Socio con ID:" + socioDto.getId() + " no encontrado")));
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
