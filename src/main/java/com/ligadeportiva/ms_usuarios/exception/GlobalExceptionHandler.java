package com.ligadeportiva.ms_usuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontrado.class)
    public ResponseEntity<String> manejarUsuarioNoEncontrado(UsuarioNoEncontrado ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({CorreoDuplicadoException.class, RutDuplicadoException.class})
    public ResponseEntity<String> manejarDuplicados(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
