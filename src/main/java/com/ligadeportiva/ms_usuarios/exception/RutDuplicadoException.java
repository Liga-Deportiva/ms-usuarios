package com.ligadeportiva.ms_usuarios.exception;

public class RutDuplicadoException extends RuntimeException{
    public RutDuplicadoException(String rut) {
        super("El RUT ya está registrado: " + rut);
    }
}
