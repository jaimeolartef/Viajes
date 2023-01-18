package com.example.viajes.Model.Dao;

import com.example.viajes.Model.Entity.Viaje;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ViajeDao extends CrudRepository<Viaje, Integer> {

    @Query("select v from Viaje v where v.finViaje = false and v.cliente.documento = :documento order by v.fechaInicio desc ")
    Set<Viaje> findByFinViajeAndCliente(Long documento);
}
