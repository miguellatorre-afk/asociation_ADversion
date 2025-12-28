package com.svalero.asociation.service;

import com.svalero.asociation.exception.ResourceNotFoundException;
import com.svalero.asociation.exception.SocioNotFoundException;
import com.svalero.asociation.model.Socio;
import com.svalero.asociation.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
        return socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException("Socio con ID " + id + " no encontrado"));
    }
    public Socio add(Socio socio) throws MethodArgumentNotValidException {
        socioRepository.save(socio);
        return socio;
    }

    public Socio modify(long id, Socio socio) {
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
