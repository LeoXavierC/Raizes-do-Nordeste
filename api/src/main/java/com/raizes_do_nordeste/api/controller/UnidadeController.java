package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.UnidadeService;
import com.raizes_do_nordeste.api.domain.Unidade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(unidadeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Unidade> salvar(@Valid @RequestBody Unidade unidade) {
        Unidade unidadeSalva = unidadeService.salvar(unidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeSalva);
    }
}