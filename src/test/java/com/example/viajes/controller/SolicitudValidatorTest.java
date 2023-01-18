package com.example.viajes.controller;

import com.example.viajes.Controller.SolicitudViajeValidatorImpl;
import com.example.viajes.Model.Entity.Persona;
import com.example.viajes.Model.Entity.TipoUsuario;
import com.example.viajes.Model.Entity.Vehiculo;
import com.example.viajes.Model.Entity.Viaje;
import com.example.viajes.Model.Service.IPersonasServices;
import com.example.viajes.Model.Service.IVehiculoService;
import com.example.viajes.Model.Service.IViajeService;
import com.example.viajes.View.Dto.PersonaDto;
import com.example.viajes.View.Dto.SolicitudViajeDto;
import com.example.viajes.View.Dto.VehiculoDto;
import com.example.viajes.View.Dto.ViajeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class SolicitudValidatorTest {

    @Mock
    IPersonasServices personasServices;

    @Mock
    IVehiculoService vehiculoService;

    @Mock
    IViajeService viajeService;

    @InjectMocks
    SolicitudViajeValidatorImpl solicitudViajeValidatorImpl;

    private static Integer TIPO_USUARIO_CLIENTE = 1;
    private static Integer TIPO_USUARIO_CONDUCTOR = 2;

    private Persona persona;
    private Persona conductor;
    private SolicitudViajeDto solicitudViajeDto;

    private Set<Vehiculo> vehiculos = new HashSet<>();

    private Set<Viaje> viajes = new HashSet<>();
    private ViajeDto viajeDto;
    private Vehiculo vehiculo;
    private Viaje viaje;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        persona = new Persona();
        persona.setDocumento(12345L);
        persona.setNombres("Prueba");
        persona.setApellidos("Persona");
        persona.setTipoUsuario(new TipoUsuario());
        persona.getTipoUsuario().setId(TIPO_USUARIO_CLIENTE);
        persona.setNumeroContacto(1234567L);

        conductor = new Persona();
        conductor.setDocumento(12346L);
        conductor.setNombres("Prueba");
        conductor.setApellidos("Conductor");
        conductor.setTipoUsuario(new TipoUsuario());
        conductor.getTipoUsuario().setId(TIPO_USUARIO_CONDUCTOR);
        conductor.setNumeroContacto(1234545L);

        solicitudViajeDto = new SolicitudViajeDto();
        solicitudViajeDto.setDocumento(12345L);
        solicitudViajeDto.setDestinoLatitud(900900);
        solicitudViajeDto.setDestinoLongitud(900923);
        solicitudViajeDto.setOrigenLatitud(900876);
        solicitudViajeDto.setOrigenLongitud(900772);

        vehiculo = new Vehiculo();
        vehiculo.setId(1);
        vehiculo.setDisponible(true);
        vehiculo.setConductor(conductor);
        vehiculo.setMarca("Mazda");
        vehiculo.setModelo("2023");
        vehiculo.setPlaca("PLA123");
        vehiculo.setDescripcion("Camioneta SUV");

        vehiculos.add(vehiculo);

        viaje = new Viaje();
        viaje.setId(1);
        viaje.setVehiculo(vehiculo);
        viaje.setValorTarifa(25000D);
        viaje.setFinViaje(true);
        viaje.setDestinoLatitud(900900);
        viaje.setDestinoLongitud(900923);
        viaje.setOrigenLatitud(900876);
        viaje.setOrigenLongitud(900772);
        viaje.setCliente(persona);
        viaje.setFechaFin(new Date(1674011487L));
        viaje.setFechaInicio(new Date(1674011470L));

        viajes.add(viaje);

        VehiculoDto vehiculoDto = new VehiculoDto();
        vehiculoDto.setId(vehiculo.getId());

        PersonaDto personaDto = new PersonaDto();
        personaDto.setDocumento(persona.getDocumento());

        viajeDto = new ViajeDto();
        viajeDto.setId(null);
        viajeDto.setVehiculo(vehiculoDto);
        viajeDto.setValorTarifa(25000D);
        viajeDto.setFinViaje(true);
        viajeDto.setDestinoLatitud(900900);
        viajeDto.setDestinoLongitud(900923);
        viajeDto.setOrigenLatitud(900876);
        viajeDto.setOrigenLongitud(900772);
        viajeDto.setCliente(personaDto);
        viajeDto.setFechaFin(new Date(1674011487L));
        viajeDto.setFechaInicio(new Date(1674011487L));

    }

    @Test
    void consultarPersonaSuccess() {
        when(personasServices.findById(anyLong())).thenReturn(Optional.of(persona));
        assertNotNull(solicitudViajeValidatorImpl.consultarPersona(anyLong()));
    }

    @Test
    void crearViajeSuccess() {
        when(personasServices.findById(solicitudViajeDto.getDocumento())).thenReturn(Optional.of(persona));
        when(vehiculoService.findByTipoUsuarioAndNotViajes()).thenReturn(vehiculos);
        when(viajeService.findByFinViaje(solicitudViajeDto.getDocumento())).thenReturn(new HashSet<>());

        viaje.setFinViaje(false);
        viaje.setFechaFin(null);
        viaje.setValorTarifa(null);
        when(viajeService.guardarViaje(any(ViajeDto.class))).thenReturn(viaje);
        vehiculo.setDisponible(false);
        when(vehiculoService.saveVehiculo(vehiculo)).thenReturn(vehiculo);
        when(vehiculoService.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));

        assertEquals(solicitudViajeValidatorImpl.crearViaje(solicitudViajeDto),
                MessageFormat.format("El conductor {0} {1} llegara en el vehiculo con placas {2}, en unos minutos, Viaje N° {3}",
                        viaje.getVehiculo().getConductor().getNombres(),
                        viaje.getVehiculo().getConductor().getApellidos(),
                        viaje.getVehiculo().getPlaca(),
                        viaje.getId()
                ));
    }

    @Test
    void crearViajeNoExisteCliente() {
        solicitudViajeDto.setDocumento(12346L);
        when(personasServices.findById(solicitudViajeDto.getDocumento())).thenReturn(Optional.empty());
        when(vehiculoService.findByTipoUsuarioAndNotViajes()).thenReturn(vehiculos);
        when(viajeService.findByFinViaje(solicitudViajeDto.getDocumento())).thenReturn(new HashSet<>());
        when(viajeService.guardarViaje(viajeDto)).thenReturn(any(Viaje.class));
        when(vehiculoService.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));

        assertEquals(solicitudViajeValidatorImpl.crearViaje(solicitudViajeDto), MessageFormat.format("El documento {0} no se encuentra asociado a ningún usuario.",solicitudViajeDto.getDocumento()));
    }

    @Test
    void crearViajeActivoViaje() {
        viaje.setFinViaje(false);
        viaje.setFechaFin(null);
        viaje.setValorTarifa(null);
        viajes.clear();
        viajes.add(viaje);
        when(personasServices.findById(solicitudViajeDto.getDocumento())).thenReturn(Optional.of(persona));
        when(vehiculoService.findByTipoUsuarioAndNotViajes()).thenReturn(vehiculos);
        when(viajeService.findByFinViaje(solicitudViajeDto.getDocumento())).thenReturn(viajes);
        when(viajeService.guardarViaje(viajeDto)).thenReturn(any(Viaje.class));
        when(vehiculoService.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));

        assertEquals(solicitudViajeValidatorImpl.crearViaje(solicitudViajeDto), MessageFormat.format("El viaje {0} se encuentra activo, no puede realizar solicitudes.", viaje.getId()));
    }

    @Test
    void crearViajeNoConductorDisponible() {
        when(personasServices.findById(solicitudViajeDto.getDocumento())).thenReturn(Optional.of(persona));
        when(vehiculoService.findByTipoUsuarioAndNotViajes()).thenReturn(new HashSet<>());
        when(viajeService.findByFinViaje(solicitudViajeDto.getDocumento())).thenReturn(new HashSet<>());
        when(viajeService.guardarViaje(viajeDto)).thenReturn(any(Viaje.class));
        when(vehiculoService.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));

        assertEquals(solicitudViajeValidatorImpl.crearViaje(solicitudViajeDto),"No hay un conductor disponible para atender tu solicitud");
    }

    @Test
    void calcularTarifaSuccess() {
        viaje.setFinViaje(false);
        viaje.setFechaFin(null);
        viaje.setValorTarifa(null);
        when(viajeService.findById(viaje.getId())).thenReturn(Optional.of(viaje));
        when(viajeService.guardarViaje(viajeDto)).thenReturn(viaje);
        when(vehiculoService.findById(viajeDto.getVehiculo().getId())).thenReturn(Optional.of(vehiculo));
        vehiculo.setDisponible(true);
        when(vehiculoService.saveVehiculo(vehiculo)).thenReturn(vehiculo);

        assertNotNull(solicitudViajeValidatorImpl.calcularTarifa(viaje.getId()));
    }

    @Test
    void calcularTarifaNoExisteViaje() {
        when(viajeService.findById(viaje.getId())).thenReturn(Optional.empty());

        assertEquals(solicitudViajeValidatorImpl.calcularTarifa(viaje.getId()),MessageFormat.format("Viaje no encontrado, por favor validar que el Id {0} no haya finalizado", 1));
    }
}
