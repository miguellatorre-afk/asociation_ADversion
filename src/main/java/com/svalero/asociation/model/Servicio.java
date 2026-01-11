package com.svalero.asociation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "servicio")
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column()
    @NotBlank
    private String description;
    @Column()
    private String periodicity;
    @Column()
    private String requisites;
    @Column(precision = 2)
    @Positive
    private Float duration;
    @Column(precision = 2)
    @Positive
    private Integer capacity;

    @ManyToMany(mappedBy = "servicio")
    private List<Participante> participantesInscritos;

    @OneToMany(mappedBy = "servicio")
    private List<Trabajador> trabajadoresAsignados;
}
