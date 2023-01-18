package com.example.viajes.Controller;

import com.example.viajes.Model.Entity.Persona;
import com.example.viajes.View.Dto.SolicitudViajeDto;
import com.example.viajes.View.Dto.ViajeDto;

import java.util.Optional;

public interface ISolicitudViajeValidator {

    Boolean consultarPersona(Long idPersona);

    //Optional<Persona> conductorDisponible();

    String crearViaje(SolicitudViajeDto solicitudViajeDto);


    String calcularTarifa(Integer idViaje);
}
