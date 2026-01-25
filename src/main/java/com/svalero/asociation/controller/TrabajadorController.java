package com.svalero.asociation.controller;

import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.service.TrabajadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TrabajadorController {
    
    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping("/trabajadores")
    public ResponseEntity<List<Trabajador>> getAll(
            @RequestParam(value = "entryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDate,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "contractType", required = false, defaultValue = "") String contractType){
        List<Trabajador> alltrabajadores = trabajadorService.findAll(entryDate, name, contractType);
        return ResponseEntity.ok(alltrabajadores);
    }

    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<Trabajador> getTrabajadorById(@PathVariable long id){
        Trabajador selectedtrabajador = trabajadorService.findById(id);
        return new ResponseEntity<>(selectedtrabajador, HttpStatus.ACCEPTED);
    }

    @PostMapping("/trabajadores")
    public ResponseEntity<Trabajador> addTrabajador(@Valid@RequestBody Trabajador trabajador){
        Trabajador newtrabajador = trabajadorService.add(trabajador);
        return new ResponseEntity<>(newtrabajador, HttpStatus.CREATED);
    }

    @PutMapping("/trabajadores/{id}")
    public ResponseEntity<Trabajador> editTrabajador(@PathVariable long id, @Valid@RequestBody Trabajador trabajador){
        Trabajador updatedtrabajador = trabajadorService.modify(id, trabajador);
        return ResponseEntity.ok(updatedtrabajador);
    }

    @DeleteMapping("/trabajadores/{id}")
    public ResponseEntity<Void> deleteTrabajador (@PathVariable long id){
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
