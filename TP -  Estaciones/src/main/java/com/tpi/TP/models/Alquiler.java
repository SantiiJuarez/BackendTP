package com.tpi.TP.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Alquileres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id" )
    private int alquilerId;

    @Column(name = "Id_cliente")
    private String clienteId;

    @Column(name = "Estado")
    private Integer estado;

    @ManyToOne
    @JoinColumn(name = "Estacion_retiro")
    private Estacion estacionRetiro;

    @ManyToOne
    @JoinColumn(name = "Estacion_devolucion")
    private Estacion estacionDevolucion;

    @Column(name = "Fecha_hora_retiro")
    private LocalDate fechaHoraRetiro;

    @Column(name = "Fecha_hora_devolucion")
    private LocalDate fechaHoraDevolucion;

    @Column(name = "Monto")
    private double monto;

    @Column(name = "Id_tarifa")
    private long tarifaId;

}
