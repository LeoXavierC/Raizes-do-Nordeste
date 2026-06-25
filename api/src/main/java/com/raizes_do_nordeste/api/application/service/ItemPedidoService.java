package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Estoque;
import com.raizes_do_nordeste.api.domain.ItemPedido;
import com.raizes_do_nordeste.api.domain.Pedido;
import com.raizes_do_nordeste.api.domain.Produto;
import com.raizes_do_nordeste.api.exception.EstoqueInsuficienteException;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.EstoqueRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.ItemPedidoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public ItemPedidoService(
            ItemPedidoRepository itemPedidoRepository,
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository,
            EstoqueRepository estoqueRepository
    ) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item do pedido não encontrado"));
    }

    public ItemPedido salvar(Long pedidoId, Long produtoId, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        Estoque estoque = estoqueRepository
                .findByProdutoIdAndUnidadeId(produtoId, pedido.getUnidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado para este produto nesta unidade"));

        if (estoque.getQuantidade() < quantidade) {
            throw new EstoqueInsuficienteException("Quantidade solicitada maior que o estoque disponível");
        }

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(produto.getPreco());

        ItemPedido itemSalvo = itemPedidoRepository.save(itemPedido);

        BigDecimal valorItem = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

        if (pedido.getValorTotal() == null) {
            pedido.setValorTotal(BigDecimal.ZERO);
        }

        pedido.setValorTotal(pedido.getValorTotal().add(valorItem));
        pedidoRepository.save(pedido);

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoque.setUltimaAtualizacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

        return itemSalvo;
    }
}