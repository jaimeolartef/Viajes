package com.example.viajes.Model.Dao;

import com.example.viajes.Model.Entity.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PersonaDao extends CrudRepository<Persona, Long> {

    @Query("select p FROM Persona p where p.documento = :idPersona")
    Persona findByIdAndIdPersona(Integer idPersona);


}
