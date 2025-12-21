package com.svalero.asociation.controller;

import com.svalero.asociation.model.Socio;
import com.svalero.asociation.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocioController {
    @Autowired
    private SocioService socioService;

    @GetMapping("/socios")
    public ResponseEntity<List<Socio>> getAll(){
        List<Socio> allsocios = socioService.findAll();
        return ResponseEntity.ok(allsocios);
    }

    @PostMapping("/socios")
    public ResponseEntity<Socio> addSocio(@RequestBody Socio socio){
        Socio newsocio = socioService.add(socio);
        return new ResponseEntity<>(socio, HttpStatus.CREATED);
    }

    @PutMapping("/socios/{id}")
    public ResponseEntity<Socio> editSocio(@PathVariable long id, @RequestBody Socio socio){
        Socio updatedsocio = socioService.modify(id, socio);
        return ResponseEntity.ok(updatedsocio);
    }

    @DeleteMapping("/socios/{id}")
    public ResponseEntity<Void> deleteSocio (@PathVariable long id){
        socioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
