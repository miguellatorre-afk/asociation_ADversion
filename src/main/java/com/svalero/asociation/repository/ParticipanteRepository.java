package com.svalero.asociation.repository;

import com.svalero.asociation.model.Participante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ParticipanteRepository extends CrudRepository<Participante, Long> {
    List <Participante> findAll();
    List<Participante>findByBirthDateAfter(LocalDate birthDate);
    List<Participante> findByTypeRel(String typeRel);
    List<Participante> findByNameStartingWithIgnoreCase(String name);
    boolean existsBydni(@Pattern(regexp = "\\d{8}[A-Z]") @NotBlank String dni);


}
