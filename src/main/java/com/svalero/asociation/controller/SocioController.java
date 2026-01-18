package com.svalero.asociation.controller;

import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class SocioController {
    @Autowired
    private SocioService socioService;

    @GetMapping("/socios")
    public ResponseEntity<List<SocioDto>> getAll(@RequestParam(value = "familyModel", required = false) String familyModel,
                                                 @RequestParam(value = "active", required = false) Boolean isActive,
                                                 @RequestParam(value = "entryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate entryDate){

        List<SocioDto> allsocios = socioService.findAll(entryDate, familyModel, isActive);
        return ResponseEntity.ok(allsocios);
    }

    @GetMapping("/socios/{id}")
    public ResponseEntity<SocioDto> getSocioById(@PathVariable long id){
        SocioDto selectedsocio = socioService.findById(id);
        return new ResponseEntity<>(selectedsocio, HttpStatus.OK);
    }

    @PostMapping("/socios")
     public ResponseEntity<Socio> addSocio(@Valid @RequestBody Socio socio){
        Socio newsocio = socioService.add(socio);
        return new ResponseEntity<>(newsocio, HttpStatus.CREATED);
    }

    @PutMapping("/socios/{id}")
    public ResponseEntity<Socio> editSocio(@PathVariable long id, @Valid @RequestBody Socio socio){
        Socio updatedsocio = socioService.modify(id, socio);
        return ResponseEntity.ok(updatedsocio);
    }

    @DeleteMapping("/socios/{id}")
    public ResponseEntity<Void> deleteSocio(@PathVariable long id){
        socioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
