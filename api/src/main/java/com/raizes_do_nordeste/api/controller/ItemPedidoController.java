package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.application.service.ItemPedidoService;
import com.raizes_do_nordeste.api.domain.ItemPedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens-pedidos")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(itemPedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemPedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemPedido> salvar(
            @RequestParam Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade
    ) {
        ItemPedido itemPedidoSalvo = itemPedidoService.salvar(pedidoId, produtoId, quantidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoSalvo);
    }
}