package com.svalero.asociation.service;

import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.SocioNotFoundException;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Socio> findAll(String familyModel, Boolean isActive, LocalDate entryDate){
        if(entryDate!= null){
            return socioRepository.findByEntryDateAfter(entryDate);
        }
        if(familyModel!=null && !familyModel.isBlank()){
            return socioRepository.findByFamilyModel(familyModel);
        }
        if (isActive!=null){
            return socioRepository.findByActive(isActive);
        }
        return socioRepository.findAll();
    }

    public Socio findById(long id) {
        return socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException("Socio con ID " + id + " no encontrado"));
    }
    public Socio add(Socio socio) {
        if(socioRepository.existsBydni(socio.getDni())){
            throw new BusinessRuleException("Un socio con DNI "+socio.getDni()+" ya existe");
        }
        socioRepository.save(socio);
        return socio;
    }

    public Socio modify(long id, Socio socio){
        Socio oldsocio = socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException("Socio con ID " + id + " no encontrado"));
        modelMapper.map(socio, oldsocio);
        return socioRepository.save(oldsocio);
    }


    public void delete(long id){
        // se utilize en vez de ::new porque hay un constructor en la clase SocioNotFoundException
        Socio socio = socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException("Socio con ID " + id + " no encontrado"));
        socioRepository.delete(socio);
    }


}
