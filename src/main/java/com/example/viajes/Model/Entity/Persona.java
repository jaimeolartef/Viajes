package com.example.viajes.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "persona")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    public Persona(Long documento) {
        this.documento = documento;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;

    @Column(name = "numerocontacto")
    private Long numeroContacto;

    @Column(name = "correoelectronico")
    private String correoElectronico;

    @ManyToOne
    private TipoUsuario tipoUsuario;
}
