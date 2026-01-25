package com.svalero.asociation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private long id;
    @Pattern(regexp = "\\d{8}[A-Z]")
    @NotBlank
    private String dni;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;
    @Pattern(regexp="\\d{3}-\\d{3}-\\d{3}")
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private Boolean active;
    @NotBlank
    private String familyModel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate;


    public long getId(){
        return id;
    }
}
