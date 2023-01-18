package com.example.viajes.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ciudad")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
}
