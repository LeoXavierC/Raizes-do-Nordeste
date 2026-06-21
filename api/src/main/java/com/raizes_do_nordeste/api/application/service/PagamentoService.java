package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Pagamento;
import com.raizes_do_nordeste.api.domain.Pedido;
import com.raizes_do_nordeste.api.infrastructure.repository.PagamentoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Pagamento processarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pagamentoRepository.existsByPedidoId(pedidoId)) {
            throw new RuntimeException("Pedido já possui pagamento processado");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setStatus("APROVADO");
        pagamento.setCodigoTransacao("MOCK-" + UUID.randomUUID());
        pagamento.setDataProcessamento(LocalDateTime.now());

        pedido.setStatus("PAGO");

        pedidoRepository.save(pedido);

        return pagamentoRepository.save(pagamento);
    }
}