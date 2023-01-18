package com.example.viajes.View.Dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDto {

    public VehiculoDto(Integer id) {
        this.id = id;
    }

    private Integer id;

    private String descripcion;

    private String placa;

    private String modelo;

    private String marca;

    private PersonaDto conductor;

    private Boolean disponible;
}
