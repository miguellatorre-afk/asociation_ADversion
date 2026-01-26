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
import java.time.LocalDate;
import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SocioDto> findAll(String familyModel,  Boolean active ,LocalDate entryDate) {

        List<Socio> socios = socioRepository.findByFilters(familyModel, active, entryDate);
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




