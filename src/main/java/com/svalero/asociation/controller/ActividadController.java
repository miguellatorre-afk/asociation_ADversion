package com.svalero.asociation.controller;

import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.service.ActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping("/actividades")
    public ResponseEntity<List<Actividad>> getAll(
            @RequestParam(value = "dayActivity", required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate dayActivity,
            @RequestParam(value = "duration", required = false) Float duration,
            @RequestParam(value = "canjoin", required = false) Boolean canjoin){
        List<Actividad> allactividades = actividadService.findAll(dayActivity, canjoin, duration);
        return ResponseEntity.ok(allactividades);
    }

    @GetMapping("/actividades/{id}")
    public ResponseEntity<Actividad> getActividadById(@PathVariable long id){
        Actividad selectedactividad = actividadService.findById(id);
        return new ResponseEntity<>(selectedactividad, HttpStatus.ACCEPTED);
    }

    @PostMapping("/actividades")
    public ResponseEntity<Actividad> addActividad(@Valid@RequestBody Actividad actividad){
        Actividad newactividad = actividadService.add(actividad);
        return new ResponseEntity<>(newactividad, HttpStatus.CREATED);
    }

    @PutMapping("/actividades/{id}")
    public ResponseEntity<Actividad> editActividad(@PathVariable long id, @Valid@RequestBody Actividad actividad){
        Actividad updatedactividad = actividadService.modify(id, actividad);
        return ResponseEntity.ok(updatedactividad);
    }

    @DeleteMapping("/actividades/{id}")
    public ResponseEntity<Void> deleteActividad (@PathVariable long id){
        actividadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
