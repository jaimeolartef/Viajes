package com.example.viajes.View.Enum;

import lombok.Getter;

@Getter
public enum ETipoUsuario {

    CLIENTE(1),
    CONDUCTOR(2);

    private final Integer idTipoUsuario;

    ETipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

}
