package com.svalero.asociation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioDto {
    @Pattern(regexp = "\\d{8}[A-Z]")
    @NotBlank
    private String dni;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;
    private String address;
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{3}")
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String familyModel;
    private Boolean active;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate = LocalDate.now();
}
