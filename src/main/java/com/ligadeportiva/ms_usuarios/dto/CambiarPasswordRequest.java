package com.ligadeportiva.ms_usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarPasswordRequest {

    private String passwordActual;
    private String passwordNueva;
}
