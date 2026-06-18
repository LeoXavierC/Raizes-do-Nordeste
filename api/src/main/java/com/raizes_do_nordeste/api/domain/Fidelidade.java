package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fidelidade")
@Getter
@Setter
@NoArgsConstructor
public class Fidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer saldoPontos;
    private String nivelCliente;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}