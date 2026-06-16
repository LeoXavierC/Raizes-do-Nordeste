package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

public class Pagamento {
    private Long id;
    private BigDecimal valor;
    private String status;
    private String codigoTransacao;

    @OneToOne
    private Pedido pedido;

}
