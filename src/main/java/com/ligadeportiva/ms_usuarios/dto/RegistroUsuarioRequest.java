package com.ligadeportiva.ms_usuarios.dto;

import com.ligadeportiva.ms_usuarios.modelo.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroUsuarioRequest {

    private String nombreUsuarios;
    private String apellidoUsuarios;
    private String rutUsuarios;
    private String correoUsuarios;
    private String passwordUsuarios;
    private LocalDate fechaNacimientoUsuarios;
    private Rol rol;
}
