package com.ligadeportiva.ms_usuarios.controller;

import com.ligadeportiva.ms_usuarios.dto.*;
import com.ligadeportiva.ms_usuarios.modelo.Rol;
import com.ligadeportiva.ms_usuarios.modelo.Usuarios;
import com.ligadeportiva.ms_usuarios.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    // 1. CREAR
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody RegistroUsuarioRequest request) {
        UsuarioResponseDTO creado = usuariosService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // 2. LEER UNO (por id)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuariosService.obtenerUsuarioPorId(id));
    }

    // 3. LEER UNO (por rut)
    @GetMapping("/rut/{rut}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(usuariosService.obtenerUsuarioPorRut(rut));
    }

    // 4. LEER TODOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuariosService.listarUsuarios());
    }

    // 5. LEER POR ROL
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorRol(@PathVariable Rol rol) {
        return ResponseEntity.ok(usuariosService.listarUsuariosPorRol(rol));
    }

    // 6. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody ActualizarUsuarioRequest request) {
        return ResponseEntity.ok(usuariosService.actualizarUsuario(id, request));
    }

    // 7. ELIMINAR (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuariosService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // 8. LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean valido = usuariosService.validarLogin(
                loginRequest.getCorreoUsuarios(),
                loginRequest.getPasswordUsuarios()
        );
        if (valido) {
            return ResponseEntity.ok("Login exitoso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    // 9. CAMBIAR CONTRASEÑA
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> cambiarPassword(@PathVariable Long id, @RequestBody CambiarPasswordRequest request) {
        usuariosService.cambiarPassword(id, request.getPasswordActual(), request.getPasswordNueva());
        return ResponseEntity.noContent().build();
    }
}
