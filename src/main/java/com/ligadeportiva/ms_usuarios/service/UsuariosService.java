package com.ligadeportiva.ms_usuarios.service;

import com.ligadeportiva.ms_usuarios.exception.UsuarioNoEncontrado;
import com.ligadeportiva.ms_usuarios.modelo.Usuarios;
import com.ligadeportiva.ms_usuarios.repository.UsuariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuarios registrarUsuario(Usuarios usuario) {
        if (usuariosRepository.existsByCorreoUsuarios(usuario.getCorreoUsuarios())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (usuariosRepository.existsByRutUsuarios(usuario.getRutUsuarios())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        // Hashear el password ANTES de guardar
        String passwordHasheado = passwordEncoder.encode(usuario.getContraseñaUsuarios());
        usuario.setContraseñaUsuarios(passwordHasheado);

        return usuariosRepository.save(usuario);
    }

    public boolean validarLogin(String correo, String passwordIngresado) {
        Usuarios usuario = usuariosRepository.findByCorreoUsuarios(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return passwordEncoder.matches(passwordIngresado, usuario.getContraseñaUsuarios());
    }

    public void eliminarUsuario (Long id){
        Usuarios usuarios = usuariosRepository.findById(id).orElseThrow(() -> new UsuarioNoEncontrado("No se pudo eliminar: Pago no encontrado con ID: " + id);
        usuariosRepository.delete(usuarios);
        log.info("Pago eliminado físicamente de la base de datos: ID {}", id);

    }
}
