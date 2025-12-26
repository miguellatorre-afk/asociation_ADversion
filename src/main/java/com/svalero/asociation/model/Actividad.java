package com.svalero.asociation.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "actividad")
@Table(name="actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate dayActivity;
    @Column (nullable = false)
    private String typeActivity;
    @Column(nullable = false, precision = 2)
    private float duration;
    @Column(nullable = false)
    private boolean canJoin;
    @Column(nullable = true, precision = 2)
    private int capacity;
    @Column(nullable = true)
    private double xCoord;
    private double yCoord;
}
