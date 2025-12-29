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
@Entity(name = "actividad")
@Table(name="actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank
    private String description;
    @Column(nullable = false, name = "day_activity")
    @FutureOrPresent
    @NotBlank
    private LocalDate dayActivity;
    @Column (nullable = false, name = "type_activity")
    @NotBlank
    private String typeActivity;
    @Column(nullable = false, precision = 2)
    @Positive
    @NotBlank
    private float duration;
    @Column(nullable = false, name = "can_join")
    private boolean canJoin;
    @Column(nullable = true, precision = 2)
    @Positive
    private int capacity;
    @Column(nullable = true)
    private double xCoord;
    private double yCoord;
}
