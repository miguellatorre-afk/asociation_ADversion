package com.svalero.asociation.repository;

import com.svalero.asociation.model.Actividad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends CrudRepository<Actividad, Long> {
    List<Actividad> findAll();
}
