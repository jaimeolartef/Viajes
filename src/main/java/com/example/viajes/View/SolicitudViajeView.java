package com.example.viajes.View;

import com.example.viajes.Controller.SolicitudViajeController;
import com.example.viajes.View.Dto.SolicitudViajeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("")
public class SolicitudViajeView {

    @Autowired
    SolicitudViajeController solicitudViajeController;


    @PostMapping("/solicitarviaje")
    public String solicitarViaje(@RequestBody SolicitudViajeDto solicitudViajeDto) {
        return solicitudViajeController.solicitarViaje(solicitudViajeDto);
    }

    @PutMapping("/calculartarifaviaje/{idViaje}")
    public String calcularTarifaViaje(@PathVariable Integer idViaje) {
        return solicitudViajeController.calcularTarifaViaje(idViaje);
    }


}
