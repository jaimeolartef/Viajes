package com.example.viajes.Model.Dao;

import com.example.viajes.Model.Entity.TipoUsuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface TipoUsuarioDao extends CrudRepository<TipoUsuario, Integer> {
}
