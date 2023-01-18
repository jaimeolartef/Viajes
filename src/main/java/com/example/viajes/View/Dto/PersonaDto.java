package com.example.viajes.View.Dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto {

    public PersonaDto(Long documento) {
        this.documento = documento;
    }

    private Long documento;

    private String nombres;

    private String apellidos;

    private Date fechaNacimiento;

    private Long numeroContacto;

    private String correoElectronico;

    private TipoUsuarioDto tipoUsuario;
}
