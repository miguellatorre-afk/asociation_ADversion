package com.svalero.asociation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inscripcion_actividad")
@Entity(name = "inscripcion_actividad")
public class InscripcionActividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate createdAt;
    private String state;
    private float price;
}
