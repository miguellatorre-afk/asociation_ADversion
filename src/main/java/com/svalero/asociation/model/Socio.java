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
    private long id; // bugfix a futuro, en postman se puede cambiar toda un registro con POST si editas la id
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = true)
    private String email;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 9, unique = true)
    private String phoneNumber;
    @Column(nullable = true, name = "family_model")
    private String FamilyModel;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private LocalDate entryDate;
    @Column(nullable = true)
    private LocalDate outDate;
}
