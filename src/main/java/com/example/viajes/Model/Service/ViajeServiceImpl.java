package com.example.viajes.Model.Service;

import com.example.viajes.Model.Dao.ViajeDao;
import com.example.viajes.Model.Entity.Persona;
import com.example.viajes.Model.Entity.Vehiculo;
import com.example.viajes.Model.Entity.Viaje;
import com.example.viajes.View.Dto.ViajeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class ViajeServiceImpl implements IViajeService {
    @Autowired
    private ViajeDao viajeDao;

    @Override
    public Set<Viaje> findByFinViaje(Long documento) {
        return viajeDao.findByFinViajeAndCliente(documento);
    }

    @Override
    public Optional<Viaje> findById(Integer id) {
        return viajeDao.findById(id);
    }

    @Override
    public Viaje guardarViaje(ViajeDto viajeDto) {

        try {
            return viajeDao.save(Viaje.builder()
                    .id(viajeDto.getId())
                    .finViaje(viajeDto.isFinViaje())
                    .cliente(new Persona(viajeDto.getCliente().getDocumento()))
                    .vehiculo(new Vehiculo(viajeDto.getVehiculo().getId()))
                    .origenLatitud(viajeDto.getOrigenLatitud())
                    .origenLongitud(viajeDto.getOrigenLongitud())
                    .destinoLatitud(viajeDto.getDestinoLatitud())
                    .destinoLongitud(viajeDto.getDestinoLongitud())
                    .fechaInicio(viajeDto.getFechaInicio())
                    .fechaFin(viajeDto.getFechaFin())
                    .valorTarifa(viajeDto.getValorTarifa())
                    .build());
        } catch (Exception e) {
            return null;
        }
    }
}
