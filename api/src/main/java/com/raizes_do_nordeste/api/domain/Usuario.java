package com.raizes_do_nordeste.api.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "usuarios")
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private String telefone;
    private Boolean consentimentoLGPD;
}
