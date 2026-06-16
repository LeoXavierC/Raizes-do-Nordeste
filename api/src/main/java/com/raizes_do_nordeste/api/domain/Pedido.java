package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {
    private Long id;
    private String status;
    private String canalPedido;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Unidade unidade;

}
