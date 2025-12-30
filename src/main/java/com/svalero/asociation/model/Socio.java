package com.svalero.asociation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(nullable = true)
    @NotBlank
    private String email;
    @Column()
    private String address;
    @Column(name = "phone_number")
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{3}")
    @NotBlank
    private String phoneNumber;
    @Column(nullable = true, name = "family_model")
    private String FamilyModel;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate = LocalDate.now();
    @Column(nullable = true)
    @Null
    private LocalDate outDate;
}
