package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.Pedido;
import com.raizes_do_nordeste.api.domain.Unidade;
import com.raizes_do_nordeste.api.domain.Usuario;
import com.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.raizes_do_nordeste.api.infrastructure.repository.PedidoRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.UnidadeRepository;
import com.raizes_do_nordeste.api.infrastructure.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
    }

    public Pedido salvar(Pedido pedido) {
        if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário do pedido é obrigatório");
        }

        if (pedido.getUnidade() == null || pedido.getUnidade().getId() == null) {
            throw new IllegalArgumentException("Unidade do pedido é obrigatória");
        }

        Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Unidade unidade = unidadeRepository.findById(pedido.getUnidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));

        pedido.setUsuario(usuario);
        pedido.setUnidade(unidade);
        pedido.setDataCriacao(LocalDateTime.now());

        if (pedido.getStatus() == null) {
            pedido.setStatus("AGUARDANDO_PAGAMENTO");
        }

        if (pedido.getValorTotal() == null) {
            pedido.setValorTotal(BigDecimal.ZERO);
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        logger.info(
                "Pedido criado: pedidoId={}, usuarioId={}, unidadeId={}, canal={}, status={}, valorTotal={}",
                pedidoSalvo.getId(),
                usuario.getId(),
                unidade.getId(),
                pedidoSalvo.getCanalPedido(),
                pedidoSalvo.getStatus(),
                pedidoSalvo.getValorTotal()
        );

        return pedidoSalvo;
    }
}