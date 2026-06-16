package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.OneToOne;

public class Fidelidade {
    private Long id;
    private Integer saldoPontos;

    @OneToOne
    private Usuario usuario;
}
