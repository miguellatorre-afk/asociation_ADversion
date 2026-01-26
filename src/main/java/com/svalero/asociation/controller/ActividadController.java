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
            @RequestParam(value = "canJoin", required = false) Boolean canJoin,
            @RequestParam(value = "duration", required = false) Float duration){
        List<Actividad> allactividades = actividadService.findAll(dayActivity, canJoin, duration);
        return ResponseEntity.ok(allactividades);
    }

    @GetMapping("/actividades/{id}")
    public ResponseEntity<Actividad> getActividadById(@PathVariable long id){
        Actividad selectedactividad = actividadService.findById(id);
        if (selectedactividad == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(selectedactividad, HttpStatus.OK);
    }

    @PostMapping("/actividades")
    public ResponseEntity<Actividad> addActividad(@Valid@RequestBody Actividad actividad){
        Actividad newactividad = actividadService.add(actividad);
        return new ResponseEntity<>(newactividad, HttpStatus.CREATED);
    }

    @PutMapping("/actividades/{id}")
    public ResponseEntity<Actividad> editActividad(@PathVariable long id, @Valid@RequestBody Actividad actividad){
        Actividad updatedactividad = actividadService.modify(id, actividad);
        if (updatedactividad == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedactividad);
    }

    @DeleteMapping("/actividades/{id}")
    public ResponseEntity<Void> deleteActividad (@PathVariable long id){
        actividadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
