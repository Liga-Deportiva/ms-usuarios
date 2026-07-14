package com.ligadeportiva.ms_usuarios.exception;

public class CorreoDuplicadoException extends RuntimeException{

    public CorreoDuplicadoException(String correo) {
        super("El correo ya está registrado: " + correo);
    }
}
