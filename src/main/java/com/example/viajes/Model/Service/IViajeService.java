package com.example.viajes.Model.Service;

import com.example.viajes.Model.Entity.Viaje;
import com.example.viajes.View.Dto.ViajeDto;

import java.util.Optional;
import java.util.Set;

public interface IViajeService {

    Set<Viaje> findByFinViaje(Long documento);
    Optional<Viaje> findById(Integer id);
    Viaje guardarViaje(ViajeDto viajeDto);
}
