package com.svalero.asociation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trabajador {
    private long id;
    private String dni;
    private String name;
    private String surname;
    private String email;
    private LocalDate entryData;
    private LocalDate birthDate;
    private String phoneNumber;
}
