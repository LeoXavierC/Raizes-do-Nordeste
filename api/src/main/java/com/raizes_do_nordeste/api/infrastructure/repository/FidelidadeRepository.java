package com.raizes_do_nordeste.api.infrastructure.repository;

import com.raizes_do_nordeste.api.domain.Fidelidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FidelidadeRepository extends JpaRepository<Fidelidade, Long> {

    Optional<Fidelidade> findByUsuarioId(Long usuarioId);
}