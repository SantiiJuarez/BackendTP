package com.tpi.TP.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tarifas")
public class Tarifas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "Tipo_tarifa")
    private int tipoTarifa;

    @Column(name = "Definicion")
    private String definicion;

    @Column(name = "Dia_semana")
    private int diaSemana;

    @Column(name = "Dia_mes")
    private int diaMes;

    @Column(name = "Mes")
    private int mes;

    @Column(name = "Anio")
    private int anio;

    @Column(name = "Monto_fijo_alquiler")
    private int montoFijoAlquiler;

    @Column(name = "Monto_minuto_fraccion")
    private double montoFijoFraccion;

    @Column(name = "Monto_km")
    private double montoKm;

    @Column(name = "Monto_hora")
    private double montoHora;
}
