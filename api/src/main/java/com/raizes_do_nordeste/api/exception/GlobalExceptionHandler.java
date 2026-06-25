package com.raizes_do_nordeste.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> handleEstoqueInsuficiente(EstoqueInsuficienteException exception) {
        Map<String, Object> response = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "erro", "Estoque insuficiente",
                "mensagem", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNaoEncontrado(RecursoNaoEncontradoException exception) {
        Map<String, Object> response = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "erro", "Recurso não encontrado",
                "mensagem", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PagamentoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handlePagamentoDuplicado(PagamentoDuplicadoException exception) {
        Map<String, Object> response = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "erro", "Pagamento duplicado",
                "mensagem", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<Map<String, Object>> handleCredenciaisInvalidas(CredenciaisInvalidasException exception) {
        Map<String, Object> response = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "erro", "Credenciais inválidas",
                "mensagem", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}