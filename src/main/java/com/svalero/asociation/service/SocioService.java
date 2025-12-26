package com.svalero.asociation.service;

import com.svalero.asociation.model.Socio;
import com.svalero.asociation.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Socio> findAll(){
        return socioRepository.findAll();
    }

    public Socio findById(long id) {
        Socio foundsocio = socioRepository.findById(id).orElseThrow();
        return foundsocio;
    }
    public Socio add(Socio socio) {
        socioRepository.save(socio);
        return socio;
    }

    public Socio modify(long id, Socio socio) {
        Socio oldsocio = socioRepository.findById(id).orElseThrow();
        modelMapper.map(socio, oldsocio);
        return socioRepository.save(oldsocio);
    }


    public void delete(long id){
        Socio socio = findById(id);
        socioRepository.delete(socio);
    }
}
