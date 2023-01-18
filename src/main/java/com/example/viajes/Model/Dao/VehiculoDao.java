package com.example.viajes.Model.Dao;

import com.example.viajes.Model.Entity.Vehiculo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface VehiculoDao extends CrudRepository<Vehiculo, Integer> {

    @Query("select v from Vehiculo v where v.disponible = true")
    Set<Vehiculo> findByDisponible();

}
