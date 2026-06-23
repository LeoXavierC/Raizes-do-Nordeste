package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.FidelidadeService;
import com.raizes_do_nordeste.api.domain.Fidelidade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelidade")
public class FidelidadeController {

    private final FidelidadeService fidelidadeService;

    public FidelidadeController(FidelidadeService fidelidadeService) {
        this.fidelidadeService = fidelidadeService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Fidelidade> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(fidelidadeService.buscarPorUsuarioId(usuarioId));
    }
}