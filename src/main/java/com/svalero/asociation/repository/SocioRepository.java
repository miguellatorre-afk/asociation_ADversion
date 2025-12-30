package com.svalero.asociation.repository;

import com.svalero.asociation.model.Socio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends CrudRepository<Socio, Long> {
    List<Socio> findAll();

    boolean existsBydni(@Pattern(regexp = "\\d{8}[A-Z]") @NotBlank String dni);
}
