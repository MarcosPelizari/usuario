package com.java.usuario.business;

import org.springframework.stereotype.Service;

import com.java.usuario.business.dto.UsuarioDTO;
import com.java.usuario.infrastructure.entity.Usuario;
import com.java.usuario.infrastructure.repository.UsuarioRepository;
import com.java.usuario.business.converter.UsuarioConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConveter;

    public UsuarioDTO salvUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConveter.paraUsuario(usuarioDTO);

        return usuarioConveter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
