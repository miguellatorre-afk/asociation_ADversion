package com.svalero.asociation.service;

import com.svalero.asociation.exception.ActividadNotFoundException;
import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.repository.ActividadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Actividad> findAll(LocalDate dayActivity, Boolean canjoin, Float duration) {

        if(dayActivity!=null){// necesita convertir LocalDate a String
            return actividadRepository.findByDayActivity(dayActivity);
        }
        if(canjoin!=null){
            return actividadRepository.findByCanJoin(canjoin);
        }
        if (duration!= null){
            return actividadRepository.findByDuration(duration);
        }
        return actividadRepository.findAll();
    }

    public Actividad findById(long id) {
        Actividad foundactividad = actividadRepository.findById(id).orElseThrow(() -> new ActividadNotFoundException("Actividad con ID:" + id + "not found"));
        return foundactividad;
    }

    public Actividad add(Actividad actividad) {
        actividadRepository.save(actividad);
        return actividad;
    }

    public Actividad modify(long id, Actividad actividad) {
        Actividad oldactividad = actividadRepository.findById(id).orElseThrow(() -> new ActividadNotFoundException("Actividad con ID:" + id + "not found"));
        modelMapper.map(actividad, oldactividad);
        return actividadRepository.save(oldactividad);
    }

    public void delete(long id) {
        Actividad actividad = actividadRepository.findById(id).orElseThrow(() -> new ActividadNotFoundException("Actividad con ID:" + id + "not found"));
        actividadRepository.delete(actividad);
    }


}
