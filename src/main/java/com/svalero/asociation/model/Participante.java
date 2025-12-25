package com.svalero.asociation.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false)
    private String name;
    private String surnames;
    private String email;
    private LocalDate birthDate;
    private LocalDate entryDate;
    @Column(columnDefinition = "TEXT")
    private String needs;
    private String typeRel;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    private Socio socioResponsable;
}
