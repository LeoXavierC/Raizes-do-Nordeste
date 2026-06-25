package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Estoque;
import com.raizes_do_nordeste.api.domain.Produto;
import com.raizes_do_nordeste.api.domain.Unidade;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.EstoqueRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.ProdutoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;

    public EstoqueService(
            EstoqueRepository estoqueRepository,
            ProdutoRepository produtoRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));
    }

    public Estoque salvar(Long produtoId, Long unidadeId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));

        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setUnidade(unidade);
        estoque.setQuantidade(quantidade);
        estoque.setUltimaAtualizacao(LocalDateTime.now());

        return estoqueRepository.save(estoque);
    }
}