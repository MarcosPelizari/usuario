package com.java.usuario.business.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.java.usuario.business.dto.EnderecoDTO;
import com.java.usuario.business.dto.TelefoneDTO;
import com.java.usuario.business.dto.UsuarioDTO;
import com.java.usuario.infrastructure.entity.Endereco;
import com.java.usuario.infrastructure.entity.Telefone;
import com.java.usuario.infrastructure.entity.Usuario;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
        .nome(usuarioDTO.getNome())
        .email(usuarioDTO.getEmail())
        .senha(usuarioDTO.getSenha())
        .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
        .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
        .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTO) {
        return enderecoDTO.stream()
            	.map(this::paraEndereco)
                .toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
            .rua(enderecoDTO.getRua())
            .numero(enderecoDTO.getNumero())
            .complemento(enderecoDTO.getComplemento())
            .cep(enderecoDTO.getCep())
            .cidade(enderecoDTO.getCidade())
            .estado(enderecoDTO.getEstado())
            .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream()
            .map(this::paraTelefone)
            .toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
            .ddd(telefoneDTO.getDdd())
            .numero(telefoneDTO.getNumero())
            .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .senha(usuario.getSenha())
        .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
        .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
        .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> endereco) {
        return endereco.stream()
            	.map(this::paraEnderecoDTO)
                .toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
            .rua(endereco.getRua())
            .numero(endereco.getNumero())
            .complemento(endereco.getComplemento())
            .cep(endereco.getCep())
            .cidade(endereco.getCidade())
            .estado(endereco.getEstado())
            .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefone) {
        return telefone.stream()
            .map(this::paraTelefoneDTO)
            .toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
            .ddd(telefone.getDdd())
            .numero(telefone.getNumero())
            .build();
    }

}
