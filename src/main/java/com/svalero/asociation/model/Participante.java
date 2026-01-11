package com.svalero.asociation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participantes")
@Entity(name = "participantes")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @Pattern(regexp = "\\d{8}[A-Z]")
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
    @NotBlank
    private String phoneNumber;
    @Column(name = "birth_date")
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Column(name = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate = LocalDate.now();
    @Column(columnDefinition = "TEXT")
    private String needs;
    private String typeRel;


    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    @Null
    private Socio socioID;

    @ManyToMany(mappedBy = "participante")
    private List<Actividad> actividadesInscritas;

    @ManyToMany(mappedBy = "participante")
    private List<Servicio> serviciosSolicitados;
}
