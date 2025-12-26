package com.svalero.asociation.controller;

import com.svalero.asociation.model.Servicio;
import com.svalero.asociation.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @GetMapping("/servicios")
    public ResponseEntity<List<Servicio>> getAll(){
        List<Servicio> allservicios = servicioService.findAll();
        return ResponseEntity.ok(allservicios);
    }

    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable long id){
        Servicio selectedservicio = servicioService.findById(id);
        return new ResponseEntity<>(selectedservicio, HttpStatus.ACCEPTED);
    }

    @PostMapping("/servicios")
    public ResponseEntity<Servicio> addServicio(@RequestBody Servicio servicio){
        Servicio newservicio = servicioService.add(servicio);
        return new ResponseEntity<>(newservicio, HttpStatus.CREATED);
    }

    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicio> editServicio(@PathVariable long id, @RequestBody Servicio servicio){
        Servicio updatedservicio = servicioService.modify(id, servicio);
        return ResponseEntity.ok(updatedservicio);
    }

    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> deleteServicio (@PathVariable long id){
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
