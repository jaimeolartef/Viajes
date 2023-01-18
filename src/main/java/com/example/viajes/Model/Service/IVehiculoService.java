package com.example.viajes.Model.Service;

import com.example.viajes.Model.Entity.Persona;
import com.example.viajes.Model.Entity.Vehiculo;

import java.util.Optional;
import java.util.Set;

public interface IVehiculoService {

    Optional<Vehiculo> findById(Integer id);

    Set<Vehiculo> findByTipoUsuarioAndNotViajes();

    Vehiculo saveVehiculo(Vehiculo vehiculo);
}
