package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Produto;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}