package com.svalero.asociation.controller;

import com.svalero.asociation.dto.ParticipanteDto;
import com.svalero.asociation.dto.SocioDto;
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

    @Autowired
    private SocioService socioService;

    @GetMapping("/participantes")
    public ResponseEntity<List<ParticipanteDto>> getAll(
            @RequestParam(value = "birthDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "typeRel",required = false) String typeRel)
    {
        List<ParticipanteDto> allparticipantes = participanteService.findAll(birthDate, name, typeRel);
        return ResponseEntity.ok(allparticipantes);
    }

    @GetMapping("/participantes/{id}")
    public ResponseEntity<ParticipanteDto> getParticipanteById(@PathVariable long id) {
        ParticipanteDto selectedparticipante = participanteService.findById(id);
        return new ResponseEntity<>(selectedparticipante, HttpStatus.ACCEPTED);
    }

    @PostMapping("/participantes")
    public ResponseEntity<Participante> addParticipante(@Valid@RequestBody ParticipanteDto participanteDto) throws MethodArgumentNotValidException {
        SocioDto socioDto = socioService.findById(participanteDto.getSocioID());
        Participante newparticipante = participanteService.add(participanteDto, socioDto);
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
