package com.tpi.TP.DTOs;

import com.tpi.TP.models.Estacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerDTO {
    private long alquilerId;
    private long clienteId;
    private int estado;
    private Estacion estacionRetiro;
    private Estacion estacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;
    private double monto;
    private long tarifaId;
}
