package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.EstoqueService;
import com.raizes_do_nordeste.api.domain.Estoque;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Estoque> salvar(
            @RequestParam Long produtoId,
            @RequestParam Long unidadeId,
            @RequestParam Integer quantidade
    ) {
        Estoque estoque = estoqueService.salvar(produtoId, unidadeId, quantidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(estoque);
    }
}