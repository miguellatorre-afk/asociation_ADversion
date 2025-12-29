package com.svalero.asociation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "socios")
@Entity(name = "socios")
public class Socio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id; // bugfix a futuro, en postman se puede cambiar toda un registro con POST si editas la id
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
    @Column(nullable = true)
    @NotBlank
    private String email;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 9, unique = true, name = "phone_number")
    @Pattern(regexp="\\d{3}\\d{3}-\\d{3}")
    @NotBlank
    private String phoneNumber;
    @Column(nullable = true, name = "family_model")
    private String FamilyModel;
    @Column(nullable = true, name = "is-active")
    private boolean isActive;
    @Column(nullable = false, name = "entry-date")
    private LocalDate entryDate;
    @Column(nullable = true)
    @Null
    private LocalDate outDate;
}
