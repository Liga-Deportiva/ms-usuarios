package com.ligadeportiva.ms_usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String correoUsuarios;
    private String passwordUsuarios;
}
