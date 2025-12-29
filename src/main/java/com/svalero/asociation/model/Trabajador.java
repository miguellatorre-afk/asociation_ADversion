package com.svalero.asociation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Pattern(regexp = "^\\\\d{8}[A-Z]$")
    @NotBlank
    private String dni;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String surname;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;
    @Column(nullable = false, length = 9, unique = true, name = "phone_number")
    @Pattern(regexp="\\d{3}\\d{3}-\\d{3}")
    @NotBlank
    private String phoneNumber;
    @Column(nullable = true, name = "birth_date")
    @NotBlank
    @Past
    private LocalDate birthDate;
    @Column(nullable = false, name = "entry_date")
    @PastOrPresent
    private LocalDate entryDate;
}
