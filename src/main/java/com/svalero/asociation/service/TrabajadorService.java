package com.svalero.asociation.service;

import com.svalero.asociation.controller.SocioController;
import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.ServicioNotFoundException;
import com.svalero.asociation.exception.TrabajadorNotFoundException;
import com.svalero.asociation.model.Participante;
import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.repository.TrabajadorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;
    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(TrabajadorService.class);

    public List<Trabajador> findAll(LocalDate entryDate, String name, String contractType){
        List<Trabajador> trabajadores = trabajadorRepository.findByFilters(entryDate, name, contractType);
        logger.info("Searching Trabajador with filters: {} {} {}", entryDate, name, contractType);
        return trabajadores;
    }

    public Trabajador findById(long id) {
        Trabajador foundtrabajador = trabajadorRepository.findById(id).orElseThrow(()-> new TrabajadorNotFoundException("Trabajador con la ID:"+ id+ "no encontrado"));
        logger.info("Found Trabajador with ID: {} ", id);
        return foundtrabajador;
    }

    public Trabajador add(Trabajador trabajador) {
        if(trabajadorRepository.existsBydni(trabajador.getDni())){
            String dni = trabajador.getDni();
            logger.warn(" {} already exists", dni);
            throw new BusinessRuleException("Un socio con DNI "+trabajador.getDni()+" ya existe");
        }
        trabajadorRepository.save(trabajador);
        long id = trabajador.getId();
        logger.info("Created Trabajador with ID: {} ", id);
        return trabajador;
    }

    public Trabajador modify(long id, Trabajador trabajador) {
        Trabajador oldtrabajador = trabajadorRepository.findById(id).orElseThrow(()-> new TrabajadorNotFoundException("Trabajador con la ID:"+ id+ "no encontrado"));
        modelMapper.map(trabajador, oldtrabajador);
        logger.info("Updated Trabajador with ID: {} ", id);
        return trabajadorRepository.save(oldtrabajador);
    }

    public void delete(long id) {
        Trabajador trabajador = trabajadorRepository.findById(id).orElseThrow(()-> new TrabajadorNotFoundException("Trabajador con la ID:"+ id+ "no encontrado"));
        logger.info("Deleted Trabajador with ID: {} ", id);
        trabajadorRepository.delete(trabajador);
    }
}
