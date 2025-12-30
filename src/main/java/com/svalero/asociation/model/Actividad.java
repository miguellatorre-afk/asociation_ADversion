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
@Entity(name = "actividad")
@Table(name="actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column()
    @NotBlank
    private String description;
    @Column(name = "day_activity")
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayActivity;
    @Column (name = "type_activity")
    @NotBlank
    private String typeActivity;
    @Column(precision = 2)
    @Positive
    private float duration;
    @Column(name = "can_join")
    private boolean canJoin;
    @Column(precision = 2)
    @Positive
    private int capacity;
    @Column(nullable = true)
    private double xCoord;
    private double yCoord;
}
