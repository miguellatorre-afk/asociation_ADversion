package com.svalero.asociation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "socios")
@Entity(name = "socios")
public class Socio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String dni;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private LocalDate birthDate;
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @Column
    private String civilState;
    @Column
    private boolean isDivorced;
    @Column
    private LocalDate entryDate;
    @Column
    private LocalDate outDate;


}
