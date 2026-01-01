package com.svalero.asociation.repository;

import com.svalero.asociation.model.Servicio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends CrudRepository<Servicio, Long>{
    List<Servicio> findAll();

    List<Servicio> findByDuration(Float duration);

    List<Servicio> findByPeriodicity(String periodicity);

    List<Servicio> findByCapacity(Integer capacity);
}
