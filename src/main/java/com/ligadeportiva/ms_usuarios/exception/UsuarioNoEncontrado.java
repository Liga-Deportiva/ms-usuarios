package com.ligadeportiva.ms_usuarios.exception;

public class UsuarioNoEncontrado extends RuntimeException {

    public UsuarioNoEncontrado(Long id) {
        super("Usuario no encontrado con id: " + id);
    }

    public UsuarioNoEncontrado(String rut) {
        super("Usuario no encontrado con rut: " + rut);
    }
}
