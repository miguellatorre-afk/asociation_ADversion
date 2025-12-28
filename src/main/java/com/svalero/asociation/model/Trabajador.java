package com.svalero.asociation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trabajadores")
@Entity(name = "trabajadores")
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true, length = 9)
    private String dni;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 9, unique = true)
    private String phoneNumber;
    @Column(nullable = true, name = "birth_date")
    private LocalDate birthDate;
    @Column(nullable = false, name = "entry_date")
    private LocalDate entryDate;
}
