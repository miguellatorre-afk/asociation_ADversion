package com.svalero.asociation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDto {
    @NotBlank(message = "necesita una descripción")
    private String description;

    private LocalDate dayActivity;
    private String typeActivity;
    @NotNull(message = "necesita una duración")
    private Float duration;
    @NotNull(message = "debe inidcarse si se puede unir o no")
    private Boolean canJoin;
    @NotNull(message = "necesita una capacidad")
    private Integer capacity;
}
