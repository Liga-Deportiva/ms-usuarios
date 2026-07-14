package com.ligadeportiva.ms_usuarios.service;

import com.ligadeportiva.ms_usuarios.dto.UsuarioResponseDTO;
import com.ligadeportiva.ms_usuarios.exception.CorreoDuplicadoException;
import com.ligadeportiva.ms_usuarios.exception.RutDuplicadoException;
import com.ligadeportiva.ms_usuarios.exception.UsuarioNoEncontrado;
import com.ligadeportiva.ms_usuarios.modelo.Rol;
import com.ligadeportiva.ms_usuarios.modelo.Usuarios;
import com.ligadeportiva.ms_usuarios.repository.UsuariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. CREAR
    public Usuarios registrarUsuario(Usuarios usuario) {
        if (usuariosRepository.existsByCorreoUsuarios(usuario.getCorreoUsuarios())) {
            throw new CorreoDuplicadoException(usuario.getCorreoUsuarios());
        }
        if (usuariosRepository.existsByRutUsuarios(usuario.getRutUsuarios())) {
            throw new RutDuplicadoException(usuario.getRutUsuarios());
        }
        usuario.setCorreoUsuarios(passwordEncoder.encode(usuario.getCorreoUsuarios()));
        usuario.setActivo(true);
        return usuariosRepository.save(usuario);
    }

    // 4. ACTUALIZAR
    public Usuarios actualizarUsuario(Long id, Usuarios datosNuevos) {
        Usuarios usuario = obtenerUsuarioPorId(id);

        usuario.setNombreUsuarios(datosNuevos.getNombreUsuarios());
        usuario.setApellidoUsuarios(datosNuevos.getApellidoUsuarios());
        usuario.setCorreoUsuarios(datosNuevos.getCorreoUsuarios());
        usuario.setFechaNacimiento(datosNuevos.getFechaNacimiento());
        // el password NO se actualiza aquí, tiene su propio método

        return usuariosRepository.save(usuario);
    }

    // 5. ELIMINAR (lógico)
    public void eliminarUsuario(Long id) {
        Usuarios usuario = obtenerUsuarioPorId(id);
        usuario.setActivo(false);
        usuariosRepository.save(usuario);
    }

    // 6. VALIDAR LOGIN
    public boolean validarLogin(String correo, String passwordIngresado) {
        Usuarios usuario = usuariosRepository.findByCorreoUsuarios(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return passwordEncoder.matches(passwordIngresado, usuario.getPasswordUsuarios());
    }

    // 7. CAMBIAR CONTRASEÑA (usuario logueado)
    public void cambiarPassword(Long id, String passwordActual, String passwordNueva) {
        Usuarios usuario = obtenerUsuarioPorId(id);

        if (!passwordEncoder.matches(passwordActual, usuario.getPasswordUsuarios())) {
            throw new RuntimeException("La contraseña actual no coincide");
        }

        usuario.setPasswordUsuarios(passwordEncoder.encode(passwordNueva));
        usuariosRepository.save(usuario);
    }

    // 8. LISTAR POR ROL
    public List<Usuarios> listarUsuariosPorRol(Rol rol) {
        return usuariosRepository.findByRolAndActivoTrue(rol);
    }

    public Usuarios obtenerUsuarioPorId(Long id) {
        return usuariosRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontrado(id));
    }

    public Usuarios obtenerUsuarioPorRut(String rut) {
        return usuariosRepository.findByRutUsuarios(rut)
                .filter(Usuarios::getActivo)
                .orElseThrow(() -> new UsuarioNoEncontrado(rut));
    }

    // mapeo
    private UsuarioResponseDTO convertirADTO(Usuarios usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuarios(),
                usuario.getNombreUsuarios(),
                usuario.getApellidoUsuarios(),
                usuario.getRutUsuarios(),
                usuario.getCorreoUsuarios(),
                usuario.getRol()
        );
    }



}
