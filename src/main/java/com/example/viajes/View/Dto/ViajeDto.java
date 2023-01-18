package com.example.viajes.View.Dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViajeDto {

    private Integer id;

    private double origenLatitud;

    private double origenLongitud;

    private double destinoLatitud;

    private double destinoLongitud;

    private VehiculoDto vehiculo;

    private PersonaDto cliente;

    private Double valorTarifa;

    private boolean finViaje;

    private Date fechaInicio;

    private Date fechaFin;
}
