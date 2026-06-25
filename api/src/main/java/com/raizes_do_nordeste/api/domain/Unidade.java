package com.raizes_do_nordeste.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unidades")
@Getter
@Setter
@NoArgsConstructor
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da unidade é obrigatório")
    private String nome;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;
}