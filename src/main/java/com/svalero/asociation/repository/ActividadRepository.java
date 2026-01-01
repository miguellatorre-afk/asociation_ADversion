package com.svalero.asociation.repository;

import com.svalero.asociation.model.Actividad;
import org.springframework.cglib.core.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActividadRepository extends CrudRepository<Actividad, Long> {
    List<Actividad> findAll();

    List<Actividad>findByDayActivity(LocalDate dayActivity);

    List<Actividad> findByDuration(Float duration);

    List<Actividad> findByCanJoin(Boolean canjoin);
}
