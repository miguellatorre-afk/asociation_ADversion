package com.svalero.asociation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actividad {
    private long id;
    private String titleDescription;
    private LocalDate dayActivity;
    private String typeActivity;
    private float price;
    private double xCoord;
    private double yCoord;
}
