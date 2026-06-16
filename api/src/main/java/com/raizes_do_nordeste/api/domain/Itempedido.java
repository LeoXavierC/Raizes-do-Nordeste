package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class Itempedido {
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

}
