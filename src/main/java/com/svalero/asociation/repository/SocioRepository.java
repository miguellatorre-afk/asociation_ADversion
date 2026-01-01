package com.svalero.asociation.repository;

import com.svalero.asociation.model.Socio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SocioRepository extends CrudRepository<Socio, Long> {
    List<Socio> findAll();
    List<Socio>findByFamilyModel(String familyModel);
    List<Socio>findByActive(Boolean active);
    List<Socio>findByEntryDateAfter(LocalDate entryDate);
    boolean existsBydni(@Pattern(regexp = "\\d{8}[A-Z]") @NotBlank String dni);
}
