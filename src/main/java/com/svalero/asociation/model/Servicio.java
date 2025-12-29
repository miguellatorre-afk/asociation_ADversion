package com.svalero.asociation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "servicio")
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank
    private String description;
    @Column(nullable = true)
    private String periodicity;
    @Column(nullable = true)
    private String requisites;
    @Column(precision = 2)
    @Positive
    @NotBlank
    private float duration;
}
