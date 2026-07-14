package com.ligadeportiva.ms_usuarios.mapper;

import com.ligadeportiva.ms_usuarios.dto.UsuarioResponseDTO;
import com.ligadeportiva.ms_usuarios.modelo.Usuarios;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface UsuariosMapper {

    UsuarioResponseDTO toDTO(Usuarios usuario);

    @Mapping(target = "idUsuarios", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Usuarios toEntity(RegistroUsuarioRequest request);
}

