package com.svalero.asociation.repository;

import com.svalero.asociation.model.Participante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ParticipanteRepository extends CrudRepository<Participante, Long> {
    List <Participante> findAll();

}
