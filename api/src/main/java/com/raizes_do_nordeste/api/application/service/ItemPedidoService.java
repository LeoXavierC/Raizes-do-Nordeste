package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.ItemPedido;
import com.raizes_do_nordeste.api.domain.Pedido;
import com.raizes_do_nordeste.api.domain.Produto;
import com.raizes_do_nordeste.api.infrastructure.repository.ItemPedidoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(
            ItemPedidoRepository itemPedidoRepository,
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado"));
    }

    public ItemPedido salvar(Long pedidoId, Long produtoId, Integer quantidade) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

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

        return itemSalvo;
    }
}