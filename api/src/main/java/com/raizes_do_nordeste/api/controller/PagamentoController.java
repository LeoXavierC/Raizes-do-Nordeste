package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.PagamentoService;
import com.raizes_do_nordeste.api.domain.Pagamento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/{pedidoId}")
    public ResponseEntity<Pagamento> processarPagamento(@PathVariable Long pedidoId) {
        Pagamento pagamento = pagamentoService.processarPagamento(pedidoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }
}