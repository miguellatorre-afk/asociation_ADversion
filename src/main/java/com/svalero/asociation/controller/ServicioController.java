package com.svalero.asociation.controller;

import com.svalero.asociation.model.Servicio;
import com.svalero.asociation.service.ServicioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    private final Logger logger = LoggerFactory.getLogger(SocioController.class);


    @GetMapping("/servicios")
    public ResponseEntity<List<Servicio>> getAll(
            @RequestParam(value = "periodicity", required = false)String periodicity,
            @RequestParam(value="capacity", required = false) Integer capacity,
            @RequestParam(value="duration", required = false) Float duration ){
        List<Servicio> allservicios = servicioService.findAll(periodicity, capacity, duration);
        logger.info("GET/servicios");
        return ResponseEntity.ok(allservicios);
    }

    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable long id){
        Servicio selectedservicio = servicioService.findById(id);
        if (selectedservicio == null){
            logger.warn("Servicio of ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("GET/servicios/{id}");
        return new ResponseEntity<>(selectedservicio, HttpStatus.ACCEPTED);
    }

    @PostMapping("/servicios")
    public ResponseEntity<Servicio> addServicio(@Valid@RequestBody Servicio servicio) throws MethodArgumentNotValidException {
        Servicio newservicio = servicioService.add(servicio);
        logger.info("POST/servicios");
        return new ResponseEntity<>(newservicio, HttpStatus.CREATED);
    }


    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicio> editServicio(@PathVariable long id, @Valid @RequestBody Servicio servicio) throws MethodArgumentNotValidException{
        Servicio updatedservicio = servicioService.modify(id, servicio);
        logger.info("PUT/servicios/{id}");
        return ResponseEntity.ok(updatedservicio);
    }

    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> deleteServicio (@PathVariable long id){
        servicioService.delete(id);
        logger.info("DELETE/servicios/{id}");
        return ResponseEntity.noContent().build();
    }


}
