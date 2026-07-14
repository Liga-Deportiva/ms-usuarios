package com.ligadeportiva.ms_usuarios.service;

import com.ligadeportiva.ms_usuarios.dto.ActualizarUsuarioRequest;
import com.ligadeportiva.ms_usuarios.dto.RegistroUsuarioRequest;
import com.ligadeportiva.ms_usuarios.dto.UsuarioResponseDTO;
import com.ligadeportiva.ms_usuarios.exception.CorreoDuplicadoException;
import com.ligadeportiva.ms_usuarios.exception.RutDuplicadoException;
import com.ligadeportiva.ms_usuarios.exception.UsuarioNoEncontrado;
import com.ligadeportiva.ms_usuarios.mapper.UsuariosMapper;
import com.ligadeportiva.ms_usuarios.modelo.Rol;
import com.ligadeportiva.ms_usuarios.modelo.Usuarios;
import com.ligadeportiva.ms_usuarios.repository.UsuariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosMapper usuariosMapper;

    // 1. CREAR
    public UsuarioResponseDTO registrarUsuario(RegistroUsuarioRequest request) {
        if (usuariosRepository.existsByCorreoUsuarios(request.getCorreoUsuarios())) {
            throw new CorreoDuplicadoException(request.getCorreoUsuarios());
        }
        if (usuariosRepository.existsByRutUsuarios(request.getRutUsuarios())) {
            throw new RutDuplicadoException(request.getRutUsuarios());
        }

        Usuarios usuario = usuariosMapper.toEntity(request);
        usuario.setPasswordUsuarios(passwordEncoder.encode(request.getPasswordUsuarios()));
        usuario.setActivo(true);

        Usuarios guardado = usuariosRepository.save(usuario);
        return usuariosMapper.toDTO(guardado);
    }

    // 4. ACTUALIZAR
    public UsuarioResponseDTO actualizarUsuario(Long id, ActualizarUsuarioRequest datosNuevos) {
        Usuarios usuario = buscarEntidadPorId(id);

        usuario.setNombreUsuarios(datosNuevos.getNombreUsuarios());
        usuario.setApellidoUsuarios(datosNuevos.getApellidoUsuarios());
        usuario.setCorreoUsuarios(datosNuevos.getCorreoUsuarios());
        usuario.setFechaNacimiento(datosNuevos.getFechaNacimiento());

        Usuarios actualizado = usuariosRepository.save(usuario);
        return usuariosMapper.toDTO(actualizado);
    }

    // 5. ELIMINAR (lógico)
    public void eliminarUsuario(Long id) {
        Usuarios usuario = buscarEntidadPorId(id); // <- entidad completa
        usuario.setActivo(false);
        usuariosRepository.save(usuario);
    }



    // 6. CAMBIAR CONTRASEÑA (usuario logueado)
    public void cambiarPassword(Long id, String passwordActual, String passwordNueva) {
        Usuarios usuario = buscarEntidadPorId(id); // <- entidad completa

        if (!passwordEncoder.matches(passwordActual, usuario.getPasswordUsuarios())) {
            throw new RuntimeException("La contraseña actual no coincide");
        }

        usuario.setPasswordUsuarios(passwordEncoder.encode(passwordNueva));
        usuariosRepository.save(usuario);
    }

    // 7. VALIDAR LOGIN
    public boolean validarLogin(String correo, String passwordIngresado) {
        Usuarios usuario = usuariosRepository.findByCorreoUsuarios(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return passwordEncoder.matches(passwordIngresado, usuario.getPasswordUsuarios());
    }

    // 8. LISTAR POR ROL
    public List<UsuarioResponseDTO> listarUsuariosPorRol(Rol rol) {
        return usuariosRepository.findByRolAndActivoTrue(rol)
                .stream()
                .map(usuariosMapper::toDTO)
                .collect(Collectors.toList());
    }

    // USO INTERNO del Service (entidad completa)
    private Usuarios buscarEntidadPorId(Long id) {
        return usuariosRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontrado(id));
    }

    // USO PÚBLICO hacia el Controller (DTO, sin password)
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        return usuariosMapper.toDTO(buscarEntidadPorId(id));
    }

    public UsuarioResponseDTO obtenerUsuarioPorRut(String rut) {
        Usuarios usuario = usuariosRepository.findByRutUsuarios(rut)
                .filter(Usuarios::getActivo)
                .orElseThrow(() -> new UsuarioNoEncontrado(rut));
        return usuariosMapper.toDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuariosRepository.findByActivoTrue()
                .stream()
                .map(usuariosMapper::toDTO)
                .collect(Collectors.toList());
    }


}
