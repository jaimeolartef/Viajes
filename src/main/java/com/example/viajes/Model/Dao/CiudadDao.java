package com.example.viajes.Model.Dao;

import com.example.viajes.Model.Entity.Ciudad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CiudadDao extends CrudRepository<Ciudad, Integer> {
}
