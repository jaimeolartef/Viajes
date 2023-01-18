package com.example.viajes.Model.Service;

import com.example.viajes.Model.Dao.PersonaDao;
import com.example.viajes.Model.Entity.Persona;
import com.example.viajes.View.Enum.ETipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PersonasServiceImpl implements IPersonasServices {

    @Autowired
    private PersonaDao personaDao;

    @Override
    public Optional<Persona> findById(Long documento) {
        return personaDao.findById(documento);
    }


}
