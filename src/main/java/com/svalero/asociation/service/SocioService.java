package com.svalero.asociation.service;

import com.svalero.asociation.dto.SocioDto;
import com.svalero.asociation.exception.BusinessRuleException;
import com.svalero.asociation.exception.SocioNotFoundException;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.repository.SocioRepository;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import jakarta.persistence.criteria.Predicate; // Asegúrate de que sea este import y no java.util.function
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SocioDto> findAll(LocalDate entryDate, String familyModel, Boolean isActive) {

//        List<Socio>socios;
//        if(entryDate!= null){
//            socios = socioRepository.findByEntryDateAfter(entryDate);
//        }
//        else if(familyModel!=null && !familyModel.isBlank()){
//            socios = socioRepository.findByFamilyModel(familyModel);
//        }
//        else if (isActive!=null){
//            socios = socioRepository.findByActive(isActive);
//        }
//        else {
//            socios = socioRepository.findAll();
//        }
        List<Socio> socios = socioRepository.findByFilters(entryDate, familyModel, isActive);
        return modelMapper.map(socios, new TypeToken<List<SocioDto>>(){}.getType());
    }

        public SocioDto findById(long id) {
        Socio socioSelected = socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException("Socio con ID " + id + " no encontrado"));
        SocioDto socioDto = modelMapper.map(socioSelected, SocioDto.class);
        return socioDto;
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
        Socio socio = socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException("Socio con ID " + id + " no encontrado"));
        socioRepository.delete(socio);
    }


}




