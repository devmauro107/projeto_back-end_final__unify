
package unify.modules.notificacao.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unify.exception.ResourceNotFoundException;
import unify.modules.notificacao.dto.NotificacaoRequestDTO;
import unify.modules.notificacao.dto.NotificacaoResponseDTO;
import unify.modules.notificacao.model.Notificacao;
import unify.modules.notificacao.repository.NotificacaoRepository;
import unify.modules.pedido.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsável pelas regras de negócio
 * relacionadas ao registro e consulta de notificações.
 */
@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    /**
     * EntityManager utilizado para localizar um pedido existente.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Registra uma nova notificação vinculada a um pedido.
     */
    @Transactional
    public NotificacaoResponseDTO registrar(NotificacaoRequestDTO dados) {

        // Verifica se o pedido informado realmente existe.
        Pedido pedido = entityManager.find(Pedido.class, dados.pedidoId());

        if (pedido == null) {
            throw new ResourceNotFoundException(
                    "Pedido não encontrado com o ID: " + dados.pedidoId()
            );
        }

        // Cria a notificação e associa ao pedido encontrado.
        Notificacao notificacao = new Notificacao();

        notificacao.setPedido(pedido);
        notificacao.setMensagem(dados.mensagem());
        notificacao.setDataEnvio(LocalDateTime.now());

        // Salva a notificação no banco de dados.
        Notificacao notificacaoSalva =
                notificacaoRepository.save(notificacao);

        return NotificacaoResponseDTO.fromEntity(notificacaoSalva);
    }

    /**
     * Consulta o histórico de notificações de um pedido.
     */
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listarPorPedido(Long pedidoId) {

        // Verifica se o pedido existe antes de consultar seu histórico.
        Pedido pedido = entityManager.find(Pedido.class, pedidoId);

        if (pedido == null) {
            throw new ResourceNotFoundException(
                    "Pedido não encontrado com o ID: " + pedidoId
            );
        }

        return notificacaoRepository
                .findByPedido_IdOrderByDataEnvioDesc(pedidoId)
                .stream()
                .map(NotificacaoResponseDTO::fromEntity)
                .toList();
    }
}
