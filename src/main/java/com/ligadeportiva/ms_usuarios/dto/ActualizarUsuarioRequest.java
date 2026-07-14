package com.ligadeportiva.ms_usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarUsuarioRequest {
    private String nombreUsuarios;
    private String apellidoUsuarios;
    private String correoUsuarios;
    private LocalDate fechaNacimiento;
}
