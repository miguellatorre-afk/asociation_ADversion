package com.svalero.asociation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionActividadRequestDto {
    private float price;
    private String state;
    private long participanteId;
}
