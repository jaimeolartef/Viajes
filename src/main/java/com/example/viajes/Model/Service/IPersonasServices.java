package com.example.viajes.Model.Service;

import com.example.viajes.Model.Entity.Persona;

import java.util.Optional;
import java.util.Set;

public interface IPersonasServices {

    //Optional<Persona> findByDocumentoAndTipoUsuario(Integer documento, Integer idTipoUsuario);

    Optional<Persona> findById(Long documento);

}
