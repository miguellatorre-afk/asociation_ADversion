package com.svalero.asociation.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private Float duration;
    @Column(name = "can_join")
    private Boolean canJoin;
    @Column(precision = 2)
    @Positive
    private Integer capacity;
    @Column(nullable = true)
    private double xCoord;
    private double yCoord;

    @ManyToMany(mappedBy = "actividades")
    private List<Participante> participantesInscritos;

    @OneToMany(mappedBy = "actividad")
    @JsonBackReference(value = "actividad_trabajadores")
    private List<Trabajador> trabajadoresAsignados;

}
