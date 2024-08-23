package com.java.usuario.business;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.usuario.business.dto.EnderecoDTO;
import com.java.usuario.business.dto.TelefoneDTO;
import com.java.usuario.business.dto.UsuarioDTO;
import com.java.usuario.infrastructure.entity.Endereco;
import com.java.usuario.infrastructure.entity.Telefone;
import com.java.usuario.infrastructure.entity.Usuario;
import com.java.usuario.infrastructure.exceptions.*;
import com.java.usuario.infrastructure.repository.EnderecoRepository;
import com.java.usuario.infrastructure.repository.TelefoneRepository;
import com.java.usuario.infrastructure.repository.UsuarioRepository;
import com.java.usuario.infrastructure.security.JwtUtil;
import com.java.usuario.business.converter.UsuarioConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConveter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConveter.paraUsuario(usuarioDTO);

        return usuarioConveter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {

        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConveter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado " + email)
                            )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email){

        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualiarDadosUsuario(String token,UsuarioDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email não localizado."));
        Usuario usuario = usuarioConveter.updateUsuario(dto, usuarioEntity);

        return usuarioConveter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO dto) {

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado " + idEndereco));
        Endereco endereco = usuarioConveter.updateEndereco(dto, entity);

        return usuarioConveter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado " + idTelefone));
        Telefone telefone = usuarioConveter.updateTelefone(dto, entity);

        return usuarioConveter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }


}
