
package unify.modules.notificacao.dto;

import unify.modules.notificacao.model.Notificacao;

import java.time.LocalDateTime;

/**
 * DTO responsável por retornar os dados
 * de uma notificação cadastrada.
 */
public record NotificacaoResponseDTO(

        Long id,
        Long pedidoId,
        String mensagem,
        LocalDateTime dataEnvio

) {

    /**
     * Converte a entidade Notificacao em DTO.
     * Dessa forma, retornamos somente os dados necessários.
     */
    public static NotificacaoResponseDTO fromEntity(Notificacao notificacao) {

        return new NotificacaoResponseDTO(
                notificacao.getId(),
                notificacao.getPedido().getId(),
                notificacao.getMensagem(),
                notificacao.getDataEnvio()
        );
    }
}
