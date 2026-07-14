package com.ligadeportiva.ms_usuarios.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idUsuarios;
    private String nombreUsuarios;
    private String apellidoUsuarios;

    @Column (unique = true)
    private String rutUsuarios;

    @Column (unique = true)
    private String correoUsuarios;

    private LocalDate fechaNacimiento;
    private String passwordUsuarios;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private Boolean activo = true;

}
