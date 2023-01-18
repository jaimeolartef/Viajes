package com.example.viajes.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "origenlatitud", nullable = false)
    private double origenLatitud;

    @Column(name = "origenlongitud", nullable = false)
    private double origenLongitud;

    @Column(name = "destinolatitud", nullable = false)
    private double destinoLatitud;

    @Column(name = "destinolongitud", nullable = false)
    private double destinoLongitud;

    @ManyToOne
    private Vehiculo vehiculo;

    @ManyToOne
    private Persona cliente;

    @Column(name = "valortarifa")
    private Double valorTarifa;

    @Column(name = "finviaje")
    private boolean finViaje;

    @Column(name = "fechaInicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fechaFin")
    private Date fechaFin;
}
