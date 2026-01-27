package com.svalero.asociation.service;

import com.svalero.asociation.exception.ServicioNotFoundException;
import com.svalero.asociation.model.Servicio;
import com.svalero.asociation.repository.ServicioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ServicioService.class);


    public List<Servicio> findAll(String periodicity, Integer capacity, Float duration){
        List<Servicio> servicios = servicioRepository.findByFilters(periodicity, capacity, duration);
        logger.info("Searching with filters: {} {} {}", periodicity, capacity, duration);
        return servicios;
    }

    public Servicio findById(long id) {
        Servicio foundservicio = servicioRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Servicio con la ID:"+ id+ "no encontrado"));
        logger.debug("Fetching servicio with ID: {}", id);
        return foundservicio;
    }
    public Servicio add(Servicio servicio) {
        servicioRepository.save(servicio);
        logger.info("Successfully created new servicio with ID: {}", servicio.getId());
        return servicio;
    }

    public Servicio modify(long id, Servicio servicio) {
        Servicio oldservicio = servicioRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Servicio con la ID:"+ id+ "no encontrado"));
        logger.info("Updating servicio with ID: {}", id);
        modelMapper.map(servicio, oldservicio);
        return servicioRepository.save(oldservicio);
    }


    public void delete(long id){
        Servicio servicio = servicioRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Servicio con la ID:"+ id+ "no encontrado"));
        logger.info("Servicio with ID: {} deleted successfully", id);
        servicioRepository.delete(servicio);
    }


}



