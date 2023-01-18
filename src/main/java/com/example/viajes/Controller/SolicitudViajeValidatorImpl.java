package com.example.viajes.Controller;

import com.example.viajes.Model.Entity.Persona;
import com.example.viajes.Model.Entity.Vehiculo;
import com.example.viajes.Model.Entity.Viaje;
import com.example.viajes.Model.Service.IPersonasServices;
import com.example.viajes.Model.Service.IVehiculoService;
import com.example.viajes.Model.Service.IViajeService;
import com.example.viajes.View.Dto.PersonaDto;
import com.example.viajes.View.Dto.SolicitudViajeDto;
import com.example.viajes.View.Dto.VehiculoDto;
import com.example.viajes.View.Dto.ViajeDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Objects;


@Controller
public class SolicitudViajeValidatorImpl implements ISolicitudViajeValidator {

    @Autowired
    IPersonasServices personasServices;

    @Autowired
    IVehiculoService vehiculoService;

    @Autowired
    IViajeService viajeService;

    Integer idViaje = 0;

    private static Double VALOR_KM = 1000D;
    private static Double VALOR_MIN = 200D;
    private static Double VALOR_BASE = 3500D;
    private static int RADIO_TIERRA = 6371;

    private VehiculoDto vehiculoDto = null;

    private ViajeDto viajeDto = null;

    @Override
    public Boolean consultarPersona(Long idPersona) {
        return personasServices.findById(idPersona).isPresent();
    }

    @Override
    public String crearViaje(SolicitudViajeDto solicitudViajeDto) {
        Viaje viaje = null;
        VehiculoDto vehiculoDto = vehiculoDisponible();
        String mensaje = validarViaje(solicitudViajeDto, vehiculoDto);

        if (Strings.isNotBlank(mensaje) || Strings.isNotEmpty(mensaje)) {
            return mensaje;
        }

        viaje = viajeService.guardarViaje(ViajeDto.builder()
                .cliente(new PersonaDto(solicitudViajeDto.getDocumento()))
                .origenLatitud(solicitudViajeDto.getOrigenLatitud())
                .origenLongitud(solicitudViajeDto.getOrigenLongitud())
                .destinoLatitud(solicitudViajeDto.getDestinoLatitud())
                .destinoLongitud(solicitudViajeDto.getDestinoLongitud())
                .vehiculo(new VehiculoDto(vehiculoDto.getId()))
                .fechaInicio(new Date())
                .finViaje(false)
                .build());

        if (Objects.isNull(viaje)) {
            return "Error al procesar tu solicitud, por favor vuelve a intentarlo";
        }

        vehiculoDto.setDisponible(false);
        viaje.setVehiculo(vehiculoService.findById(viaje.getVehiculo().getId()).get());

        ocuparVehiculo(vehiculoDto);

        return MessageFormat.format("El conductor {0} {1} llegara en el vehiculo con placas {2}, en unos minutos, Viaje N° {3}",
                viaje.getVehiculo().getConductor().getNombres(),
                viaje.getVehiculo().getConductor().getApellidos(),
                viaje.getVehiculo().getPlaca(),
                viaje.getId()
        );
    }

    private VehiculoDto vehiculoDisponible() {

        vehiculoService.findByTipoUsuarioAndNotViajes().forEach(vehi -> {
            vehiculoDto = VehiculoDto.builder()
                    .id(vehi.getId())
                    .disponible(vehi.isDisponible())
                    .placa(vehi.getPlaca())
                    .marca(vehi.getMarca())
                    .descripcion(vehi.getDescripcion())
                    .modelo(vehi.getModelo())
                    .conductor(new PersonaDto(vehi.getConductor().getDocumento()))
                    .build();
        });
        return vehiculoDto;
    }

    private String validarViaje(SolicitudViajeDto solicitudViajeDto, VehiculoDto vehiculoDto) {

        if (personasServices.findById(solicitudViajeDto.getDocumento()).isEmpty()){
            return MessageFormat.format("El documento {0} no se encuentra asociado a ningún usuario.",solicitudViajeDto.getDocumento());
        }

        idViaje=0;
        viajeService.findByFinViaje(solicitudViajeDto.getDocumento()).forEach(viaje -> {
            idViaje = viaje.getId();
        });

        if (idViaje > 0) {
            return MessageFormat.format("El viaje {0} se encuentra activo, no puede realizar solicitudes.", idViaje);
        }

        if (Objects.isNull(vehiculoDto)) {
            return "No hay un conductor disponible para atender tu solicitud";
        }

        return "";
    }

    private void ocuparVehiculo(VehiculoDto vehiculoDto) {
        vehiculoService.saveVehiculo(Vehiculo.builder()
                .id(vehiculoDto.getId())
                .disponible(vehiculoDto.getDisponible())
                .placa(vehiculoDto.getPlaca())
                .marca(vehiculoDto.getMarca())
                .descripcion(vehiculoDto.getDescripcion())
                .modelo(vehiculoDto.getModelo())
                .conductor(new Persona(vehiculoDto.getConductor().getDocumento()))
                .build());
    }

    @Override
    public String calcularTarifa(Integer idViaje) {
        ViajeDto viajeDto = buscarViaje(idViaje);

        if (Objects.isNull(viajeDto) || viajeDto.isFinViaje()) {
            return MessageFormat.format("Viaje no encontrado, por favor validar que el Id {0} no haya finalizado", idViaje);
        }

        Double valorKm = valorDistancia(viajeDto.getOrigenLatitud(), viajeDto.getDestinoLatitud(), viajeDto.getOrigenLongitud(), viajeDto.getDestinoLongitud());
        Double valorTiempo = valorTiempo(viajeDto);
        viajeDto.setValorTarifa(VALOR_BASE + valorTiempo + valorKm);
        finViaje(viajeDto);
        liberarVehiculo(viajeDto.getVehiculo().getId());

        return MessageFormat.format("El valor a pagar es {0}", viajeDto.getValorTarifa());
    }

    private ViajeDto buscarViaje(Integer idViaje) {
        viajeDto = null;
        viajeService.findById(idViaje)
                .ifPresent(viaj -> {
                    viajeDto = ViajeDto.builder()
                            .id(viaj.getId())
                            .cliente(new PersonaDto(viaj.getCliente().getDocumento()))
                            .origenLatitud(viaj.getOrigenLatitud())
                            .origenLongitud(viaj.getOrigenLongitud())
                            .destinoLatitud(viaj.getDestinoLatitud())
                            .destinoLongitud(viaj.getDestinoLongitud())
                            .vehiculo(new VehiculoDto(viaj.getVehiculo().getId()))
                            .valorTarifa(viaj.getValorTarifa())
                            .fechaInicio(viaj.getFechaInicio())
                            .fechaFin(new Date())
                            .finViaje(viaj.isFinViaje())
                            .build();
                });
        return viajeDto;
    }


    private void liberarVehiculo(Integer idVehiculo) {
        vehiculoService.findById(idVehiculo).map(vehiculo -> {
            vehiculo.setDisponible(true);
            return vehiculoService.saveVehiculo(vehiculo);
        });
    }

    public void finViaje(ViajeDto viajeDto) {
        viajeDto.setFinViaje(true);
        viajeDto.setFechaFin(new Date());
        viajeService.guardarViaje(viajeDto);
    }

    private double valorDistancia(double origenLatitud, double destinoLatitud, double origenLongitud,
                            double destinoLongitud) {

        double latDistance = Math.toRadians(destinoLatitud - origenLatitud);
        double lonDistance = Math.toRadians(destinoLongitud - origenLongitud);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(origenLatitud)) * Math.cos(Math.toRadians(destinoLatitud))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = RADIO_TIERRA * c * 1000;
        return (Math.sqrt(distance / 1000)) * VALOR_KM;
    }

    private double valorTiempo(ViajeDto viajeDto) {
        return ((viajeDto.getFechaFin().getTime() - viajeDto.getFechaInicio().getTime() / (1000 * 60)) % 60) * VALOR_MIN;
    }
}
