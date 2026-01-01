package com.svalero.asociation.service;

import com.svalero.asociation.exception.ServicioNotFoundException;
import com.svalero.asociation.model.Servicio;
import com.svalero.asociation.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Servicio> findAll(String periodicity, Integer capacity, Float duration){
        if(periodicity!= null) {
            return servicioRepository.findByPeriodicity(periodicity);
        }
        if (capacity!= null){
            return servicioRepository.findByCapacity(capacity);
        }
        if (duration!= null){
            return servicioRepository.findByDuration(duration);
        }
        return servicioRepository.findAll();
    }

    public Servicio findById(long id) {
        Servicio foundservicio = servicioRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Servicio con la ID:"+ id+ "no encontrado"));
        return foundservicio;
    }
    public Servicio add(Servicio servicio) {
        servicioRepository.save(servicio);
        return servicio;
    }

    public Servicio modify(long id, Servicio servicio) {
        Servicio oldservicio = servicioRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Servicio con la ID:"+ id+ "no encontrado"));
        modelMapper.map(servicio, oldservicio);
        return servicioRepository.save(oldservicio);
    }


    public void delete(long id){
        Servicio servicio = servicioRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Servicio con la ID:"+ id+ "no encontrado"));
        servicioRepository.delete(servicio);
    }


}



