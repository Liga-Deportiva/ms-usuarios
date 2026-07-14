package com.ligadeportiva.ms_usuarios.repository;

import com.ligadeportiva.ms_usuarios.modelo.Rol;
import com.ligadeportiva.ms_usuarios.modelo.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByCorreoUsuarios(String correo);
    Optional<Usuarios> findByRutUsuarios(String rut);
    boolean existsByCorreoUsuarios(String correo);
    boolean existsByRutUsuarios(String rut);

    List<Usuarios> findByActivoTrue();
    List<Usuarios> findByRolAndActivoTrue(Rol rol);

}
