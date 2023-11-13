package com.tpi.TP.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerFinalizadoDTO {
    private long alquilerId;
    private long estacionDevolucionId;
    private String moneda;
}
