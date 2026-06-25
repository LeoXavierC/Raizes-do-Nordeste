package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Usuario;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}