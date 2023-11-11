package com.tpi.TP.DTOs;

import com.tpi.TP.models.Estacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerCreadoDTO {
    private long clienteId;
    private long estacionRetiro;
}
