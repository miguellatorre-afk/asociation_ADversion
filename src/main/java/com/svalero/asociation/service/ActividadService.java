package com.svalero.asociation.service;

import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.repository.ActividadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    public Actividad findById(long id) {
        Actividad foundactividad = actividadRepository.findById(id).orElseThrow();
        return foundactividad;
    }

    public Actividad add(Actividad actividad) {
        actividadRepository.save(actividad);
        return actividad;
    }

    public Actividad modify(long id, Actividad actividad) {
        Actividad oldactividad = actividadRepository.findById(id).orElseThrow();
        modelMapper.map(actividad, oldactividad);
        return actividadRepository.save(oldactividad);
    }

    public void delete(long id) {
        Actividad actividad = findById(id);
        actividadRepository.delete(actividad);
    }
}
