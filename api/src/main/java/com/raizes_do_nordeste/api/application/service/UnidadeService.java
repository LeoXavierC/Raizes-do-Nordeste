package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Unidade;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public List<Unidade> listarTodos() {
        return unidadeRepository.findAll();
    }

    public Unidade buscarPorId(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));
    }

    public Unidade salvar(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }
}