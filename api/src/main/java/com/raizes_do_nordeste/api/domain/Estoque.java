package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.ManyToOne;

public class Estoque {
    private Long id;
    private Integer quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Unidade unidade;
}
