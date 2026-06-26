package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Pagamento;
import com.raizes_do_nordeste.api.domain.Pedido;
import com.raizes_do_nordeste.api.exception.PagamentoDuplicadoException;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.PagamentoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PagamentoService {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final FidelidadeService fidelidadeService;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            PedidoRepository pedidoRepository,
            FidelidadeService fidelidadeService
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.fidelidadeService = fidelidadeService;
    }

    public Pagamento processarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        if (pagamentoRepository.existsByPedidoId(pedidoId)) {
            throw new PagamentoDuplicadoException("Pedido já possui pagamento processado");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setCodigoTransacao("MOCK-" + UUID.randomUUID());
        pagamento.setDataProcessamento(LocalDateTime.now());

        if (pedido.getValorTotal() == null || pedido.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            pagamento.setStatus("RECUSADO");
            pedido.setStatus("PAGAMENTO_RECUSADO");

            pedidoRepository.save(pedido);

            logger.warn(
                    "Pagamento recusado: pedidoId={}, valor={}",
                    pedido.getId(),
                    pedido.getValorTotal()
            );

            return pagamentoRepository.save(pagamento);
        }

        pagamento.setStatus("APROVADO");
        pedido.setStatus("PAGO");

        pedidoRepository.save(pedido);

        fidelidadeService.adicionarPontos(
                pedido.getUsuario().getId(),
                pedido.getValorTotal()
        );

        logger.info(
                "Pagamento aprovado: pedidoId={}, valor={}, codigoTransacao={}",
                pedido.getId(),
                pedido.getValorTotal(),
                pagamento.getCodigoTransacao()
        );

        return pagamentoRepository.save(pagamento);
    }
}