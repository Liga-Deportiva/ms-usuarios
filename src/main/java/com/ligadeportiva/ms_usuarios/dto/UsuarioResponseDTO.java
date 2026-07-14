package com.ligadeportiva.ms_usuarios.dto;

import com.ligadeportiva.ms_usuarios.modelo.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuarios;
    private String nombreUsuarios;
    private String apellidoUsuarios;
    private String rutUsuarios;
    private String correoUsuarios;
    private Rol rol;
}
