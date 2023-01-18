package com.example.viajes.Model.Service;

import com.example.viajes.Model.Dao.VehiculoDao;
import com.example.viajes.Model.Entity.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

    @Autowired
    private VehiculoDao vehiculoDao;

    @Override
    public Optional<Vehiculo> findById(Integer id) {
        Optional<Vehiculo> vehiculo = vehiculoDao.findById(id);
        vehiculo.get().getConductor().toString();
        return vehiculo;
    }

    @Override
    public Set<Vehiculo> findByTipoUsuarioAndNotViajes() {
        return vehiculoDao.findByDisponible();
    }

    @Override
    public Vehiculo saveVehiculo(Vehiculo vehiculo) {
        return vehiculoDao.save(vehiculo);
    }
}
