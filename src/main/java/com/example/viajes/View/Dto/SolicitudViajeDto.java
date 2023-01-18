package com.example.viajes.View.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
@Setter
public class SolicitudViajeDto {

    private Long documento;
    private double origenLatitud;
    private double origenLongitud;
    private double destinoLatitud;
    private double destinoLongitud;
}
