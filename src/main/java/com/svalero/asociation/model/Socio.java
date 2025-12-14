package com.svalero.asociation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Socio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String dni;
    private Spring name;
    private Spring surname;
    private String email;
    private LocalDate birthDate;
    private String direction;
    private String phoneNumber;
    private String civilState;
    private boolean isTutor;
    private LocalDate entryDate;
    private LocalDate outDate;
}
