package com.svalero.asociation.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participantes")
@Entity(name = "participantes")
public class Participante {
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
    @Column(nullable = false)
    @NotBlank
    private String email;
    @Column(nullable = false, name = "birth_date")
    @NotBlank
    @Past
    private LocalDate birthDate;
    @Column(nullable = false, name = "entry_date")
    @NotBlank
    private LocalDate entryDate;
    @Column(columnDefinition = "TEXT")
    private String needs;
    private String typeRel;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    private Socio socioID;
}
