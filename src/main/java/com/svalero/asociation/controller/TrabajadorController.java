package com.svalero.asociation.controller;

import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.service.TrabajadorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(TrabajadorController.class);

    @GetMapping("/trabajadores")
    public ResponseEntity<List<Trabajador>> getAll(
            @RequestParam(value = "entryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDate,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "contractType", required = false) String contractType){
        List<Trabajador> alltrabajadores = trabajadorService.findAll(entryDate, name, contractType);
        logger.info("GET/trabajadores");
        return ResponseEntity.ok(alltrabajadores);
    }

    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<Trabajador> getTrabajadorById(@PathVariable long id){
        Trabajador selectedtrabajador = trabajadorService.findById(id);
        if (selectedtrabajador == null){
            logger.warn("Trabajador of ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("GET/trabajadores/{id}");
        return new ResponseEntity<>(selectedtrabajador, HttpStatus.ACCEPTED);
    }

    @PostMapping("/trabajadores")
    public ResponseEntity<Trabajador> addTrabajador(@Valid@RequestBody Trabajador trabajador){
        Trabajador newtrabajador = trabajadorService.add(trabajador);
        logger.info("POST/trabajadores");
        return new ResponseEntity<>(newtrabajador, HttpStatus.CREATED);
    }

    @PutMapping("/trabajadores/{id}")
    public ResponseEntity<Trabajador> editTrabajador(@PathVariable long id, @Valid@RequestBody Trabajador trabajador){
        Trabajador updatedtrabajador = trabajadorService.modify(id, trabajador);
        logger.info("PUT/trabajadores/{id}");
        return ResponseEntity.ok(updatedtrabajador);
    }

    @DeleteMapping("/trabajadores/{id}")
    public ResponseEntity<Void> deleteTrabajador (@PathVariable long id){
        trabajadorService.delete(id);
        logger.info("DELETE/trabajadores/{id}");
        return ResponseEntity.noContent().build();
    }

}
