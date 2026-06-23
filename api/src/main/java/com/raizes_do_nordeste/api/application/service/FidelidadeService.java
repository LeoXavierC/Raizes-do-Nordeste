package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Fidelidade;
import com.raizes_do_nordeste.api.domain.Usuario;
import com.raizes_do_nordeste.api.infrastructure.repository.FidelidadeRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FidelidadeService {

    private final FidelidadeRepository fidelidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public FidelidadeService(
            FidelidadeRepository fidelidadeRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.fidelidadeRepository = fidelidadeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Fidelidade buscarPorUsuarioId(Long usuarioId) {
        return fidelidadeRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Fidelidade não encontrada para este usuário"));
    }

    public Fidelidade adicionarPontos(Long usuarioId, BigDecimal valorPago) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        int pontosGanhos = valorPago.intValue();

        Fidelidade fidelidade = fidelidadeRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Fidelidade novaFidelidade = new Fidelidade();
                    novaFidelidade.setUsuario(usuario);
                    novaFidelidade.setSaldoPontos(0);
                    novaFidelidade.setNivelCliente("BRONZE");
                    return novaFidelidade;
                });

        fidelidade.setSaldoPontos(fidelidade.getSaldoPontos() + pontosGanhos);

        if (fidelidade.getSaldoPontos() >= 100) {
            fidelidade.setNivelCliente("OURO");
        } else if (fidelidade.getSaldoPontos() >= 50) {
            fidelidade.setNivelCliente("PRATA");
        } else {
            fidelidade.setNivelCliente("BRONZE");
        }

        return fidelidadeRepository.save(fidelidade);
    }
}