package com.svalero.asociation.service;

import com.svalero.asociation.model.Participante;
import com.svalero.asociation.repository.ParticipanteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Participante> findAll(){
        return participanteRepository.findAll();
    }

    public Participante findById(long id) {
        Participante foundparticipante = participanteRepository.findById(id).orElseThrow();
        return foundparticipante;
    }

    public Participante add(Participante participante) {
        participanteRepository.save(participante);
        return participante;
    }

    public Participante modify(long id, Participante participante) {
        Participante oldparticipante = participanteRepository.findById(id).orElseThrow();
        modelMapper.map(participante, oldparticipante);
        return participanteRepository.save(oldparticipante);
    }

    public void delete(long id) {
        Participante participante = findById(id);
        participanteRepository.delete(participante);
    }
}
