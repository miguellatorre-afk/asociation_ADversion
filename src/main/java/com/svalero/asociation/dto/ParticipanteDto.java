package com.svalero.asociation.dto;

import com.svalero.asociation.model.Socio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDto {
    private long id;
    private String dni;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String needs;
    private String typeRel;
    private SocioDto socioID;



    public long getId() {
        return id;
    }
}