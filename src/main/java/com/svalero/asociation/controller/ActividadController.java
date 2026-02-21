package com.svalero.asociation.controller;

import com.svalero.asociation.dto.ActividadDto;
import com.svalero.asociation.dto.ActividadOutDto;
import com.svalero.asociation.dto.InscripcionActividadRequestDto;
import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.model.Actividad;
import com.svalero.asociation.service.ActividadService;
import com.svalero.asociation.service.InscripcionActividadService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ActividadController {

    @Autowired
    private ActividadService actividadService;
    @Autowired
    private InscripcionActividadService inscripcionActividadService;

    private final Logger logger = LoggerFactory.getLogger(ActividadController.class);

    @GetMapping("/actividades")
    public ResponseEntity<List<ActividadOutDto>> getAll(
            @RequestParam(value = "dayActivity", required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate dayActivity,
            @RequestParam(value = "canJoin", required = false) Boolean canJoin,
            @RequestParam(value = "duration", required = false) Float duration){
        logger.info("GET/actividades");
        List<ActividadOutDto> allactividades = actividadService.findAll(dayActivity, canJoin, duration);
        return ResponseEntity.ok(allactividades);
    }

    @GetMapping("/actividades/{id}")
    public ResponseEntity<ActividadOutDto> getActividadById(@PathVariable long id){
        ActividadOutDto selectedactividad = actividadService.findOutById(id);
        if (selectedactividad == null){
            logger.warn("Actividad of ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("GET/actividades/{id}");
        return new ResponseEntity<>(selectedactividad, HttpStatus.OK);
    }

    @PostMapping("/actividades")
    public ResponseEntity<ActividadOutDto> addActividad(@Valid@RequestBody ActividadDto actividad) throws MethodArgumentNotValidException {
        ActividadOutDto newActividad = actividadService.add(actividad);
        logger.info("POST/actividades");
        return new ResponseEntity<>(newActividad, HttpStatus.CREATED);
    }

    @PostMapping("/actividades/{id}/inscripciones")
    public ResponseEntity<Void> inscribirParticipante(@PathVariable long id, @Valid@RequestBody InscripcionActividadRequestDto requestDto)  throws MethodArgumentNotValidException  {
        inscripcionActividadService.inscribir(id, requestDto.getParticipanteId(), requestDto.getState(), requestDto.getPrice());
        logger.info("POST/actividades/{id}/inscripciones");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/actividades/{id}/inscripciones/{participanteId}")
    public ResponseEntity<Void> desinscribirParticipante(@PathVariable long id, @PathVariable long participanteId) {
        inscripcionActividadService.desinscribir(id, participanteId);
        logger.info("DELETE/actividades/{id}/inscripciones/{participanteId}");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actividades/{id}/participantes")
    public ResponseEntity<List<ParticipanteDto>> getParticipantesByActividad(@PathVariable long id) {
        List<ParticipanteDto> participantes = inscripcionActividadService.listarParticipantes(id);
        logger.info("GET/actividades/{id}/participantes");
        return ResponseEntity.ok(participantes);
    }

    @PutMapping("/actividades/{id}")
    public ResponseEntity<Actividad> editActividad(@PathVariable long id, @Valid@RequestBody Actividad actividad) throws MethodArgumentNotValidException{
        Actividad updatedactividad = actividadService.modify(id, actividad);
        if (updatedactividad == null){
            return ResponseEntity.notFound().build();
        }
        logger.info("PUT/actividades/{id}");
        return ResponseEntity.ok(updatedactividad);
    }

    @DeleteMapping("/actividades/{id}")
    public ResponseEntity<Void> deleteActividad (@PathVariable long id){
        actividadService.delete(id);
        logger.info("DELETE/actividades/{id}");
        return ResponseEntity.noContent().build();
    }
}
