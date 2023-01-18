package com.example.viajes.Controller;

import com.example.viajes.View.Dto.SolicitudViajeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;

@Controller
public class SolicitudViajeController {

    @Autowired
    ISolicitudViajeValidator solicitudViajeValidator;


    public String solicitarViaje(SolicitudViajeDto solicitudViajeDto) {
        return solicitudViajeValidator.crearViaje(solicitudViajeDto);
    }

    public String calcularTarifaViaje(Integer idViaje) {
        return solicitudViajeValidator.calcularTarifa(idViaje);
    }

}
