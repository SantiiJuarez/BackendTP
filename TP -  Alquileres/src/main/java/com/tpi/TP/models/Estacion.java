package com.tpi.TP.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ESTACIONES")
@Data
@NoArgsConstructor
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id" )
    private long estacionId;

    @Column(name = "Nombre")
    private String nombreEstacion;

    @Column(name = "Fecha_hora_creacion")
    private LocalDate fechaHoraCreacion;

    @Column(name = "Latitud")
    private double latitud;

    @Column(name = "Longitud")
    private double longitud;
}
