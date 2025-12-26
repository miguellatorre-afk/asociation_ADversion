package com.svalero.asociation.controller;

import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrabajadorController {
    
    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping("/trabajadores")
    public ResponseEntity<List<Trabajador>> getAll(){
        List<Trabajador> alltrabajadors = trabajadorService.findAll();
        return ResponseEntity.ok(alltrabajadors);
    }

    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<Trabajador> getTrabajadorById(@PathVariable long id){
        Trabajador selectedtrabajador = trabajadorService.findById(id);
        return new ResponseEntity<>(selectedtrabajador, HttpStatus.ACCEPTED);
    }

    @PostMapping("/trabajadores")
    public ResponseEntity<Trabajador> addTrabajador(@RequestBody Trabajador trabajador){
        Trabajador newtrabajador = trabajadorService.add(trabajador);
        return new ResponseEntity<>(newtrabajador, HttpStatus.CREATED);
    }

    @PutMapping("/trabajadores/{id}")
    public ResponseEntity<Trabajador> editTrabajador(@PathVariable long id, @RequestBody Trabajador trabajador){
        Trabajador updatedtrabajador = trabajadorService.modify(id, trabajador);
        return ResponseEntity.ok(updatedtrabajador);
    }

    @DeleteMapping("/trabajadores/{id}")
    public ResponseEntity<Void> deleteTrabajador (@PathVariable long id){
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
