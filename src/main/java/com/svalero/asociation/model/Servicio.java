package com.svalero.asociation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    private long id;
    private float price;
    private boolean hasAcces;
    private double xCoord;
    private double yCoord;
}
