package com.example.viajes.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {

    public Vehiculo(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "placa")
    private String placa;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "Marca")
    private String marca;

    @Column(name = "disponible")
    private boolean disponible;

    @OneToOne
    private Persona conductor;
}
