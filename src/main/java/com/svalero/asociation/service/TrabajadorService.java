package com.svalero.asociation.service;

import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.ServicioNotFoundException;
import com.svalero.asociation.model.Trabajador;
import com.svalero.asociation.repository.TrabajadorRepository;
import org.modelmapper.ModelMapper;
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

    public List<Trabajador> findAll(LocalDate entryDate, String name, String contractType){
        if(entryDate!= null){
            return trabajadorRepository.findByEntryDateAfter(entryDate);
        }
        if(name!=null && !name.isEmpty()){
            return trabajadorRepository.findByNameStartingWithIgnoreCase(name);
        }
        if(contractType!= null && !contractType.isEmpty()) {
            return trabajadorRepository.findByContractType(contractType);
        }
        return trabajadorRepository.findAll();
    }

    public Trabajador findById(long id) {
        Trabajador foundtrabajador = trabajadorRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Trabajador con la ID:"+ id+ "no encontrado"));
        return foundtrabajador;
    }

    public Trabajador add(Trabajador trabajador) {
        if(trabajadorRepository.existsBydni(trabajador.getDni())){
            throw new BusinessRuleException("Un socio con DNI "+trabajador.getDni()+" ya existe");
        }
        trabajadorRepository.save(trabajador);
        return trabajador;
    }

    public Trabajador modify(long id, Trabajador trabajador) {
        Trabajador oldtrabajador = trabajadorRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Trabajador con la ID:"+ id+ "no encontrado"));
        modelMapper.map(trabajador, oldtrabajador);
        return trabajadorRepository.save(oldtrabajador);
    }

    public void delete(long id) {
        Trabajador trabajador = trabajadorRepository.findById(id).orElseThrow(()-> new ServicioNotFoundException("Trabajador con la ID:"+ id+ "no encontrado"));
        trabajadorRepository.delete(trabajador);
    }
}
