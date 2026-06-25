package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Pedido;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
    }

    public Pedido salvar(Pedido pedido) {
        pedido.setDataCriacao(LocalDateTime.now());

        if (pedido.getStatus() == null) {
            pedido.setStatus("AGUARDANDO_PAGAMENTO");
        }

        if (pedido.getValorTotal() == null) {
            pedido.setValorTotal(BigDecimal.ZERO);
        }

        return pedidoRepository.save(pedido);
    }
}