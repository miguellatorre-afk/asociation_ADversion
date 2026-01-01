package com.svalero.asociation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(unique = true)
    @Pattern(regexp = "^\\d{8}[A-Z]$")
    @NotBlank
    private String dni;
    @Column()
    @NotBlank
    private String name;
    @Column()
    @NotBlank
    private String surname;
    @Column()
    @NotBlank
    private String email;
    @Column(name = "phone_number")
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{3}")
    private String phoneNumber;
    @Column(nullable = true, name = "birth_date")
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Column( name = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate;
    @Column
    private String contractType;
}
