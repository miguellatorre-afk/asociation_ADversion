package com.svalero.asociation.controller;

import com.svalero.asociation.model.Participante;
import com.svalero.asociation.service.ParticipanteService;
import com.svalero.asociation.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping("/participantes")
    public ResponseEntity<List<Participante>> getAll(
            @RequestParam(value = "birthDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "typeRel",required = false, defaultValue = "") String typeRel)
    {
        List<Participante> allparticipantes = participanteService.findAll(birthDate, name, typeRel);
        return ResponseEntity.ok(allparticipantes);
    }

    @GetMapping("/participantes/{id}")
    public ResponseEntity<Participante> getParticipanteById(@PathVariable long id) {
        Participante selectedparticipante = participanteService.findById(id);
        return new ResponseEntity<>(selectedparticipante, HttpStatus.OK);
    }

    @PostMapping("/participantes")
    public ResponseEntity<Participante> addParticipante(@Valid@RequestBody Participante participante) throws MethodArgumentNotValidException {
        Participante newparticipante = participanteService.add(participante);
        return new ResponseEntity<>(newparticipante, HttpStatus.CREATED);
    }

    @PutMapping("/participantes/{id}")
    public ResponseEntity<Participante> editParticipante(@PathVariable long id, @Valid@RequestBody Participante participante) throws MethodArgumentNotValidException{
        Participante updatedparticipante = participanteService.modify(id, participante);
        return ResponseEntity.ok(updatedparticipante);
    }

    @DeleteMapping("/participantes/{id}")
    public ResponseEntity<Void> deleteParticipante (@PathVariable long id){
        participanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
