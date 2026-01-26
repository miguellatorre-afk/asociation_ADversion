package com.svalero.asociation.service;

import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.exception.ActividadNotFoundException;
import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.repository.ActividadRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Actividad> findAll(LocalDate dayActivity, Boolean canJoin, Float duration) {
        List<Actividad> actividades = actividadRepository.findByFilters(dayActivity, canJoin, duration);
        return actividades;
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
