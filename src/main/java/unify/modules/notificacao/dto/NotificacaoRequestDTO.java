
package unify.modules.notificacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO responsável por receber os dados
 * necessários para registrar uma notificação.
 */
public record NotificacaoRequestDTO(

        @NotNull(message = "O ID do pedido é obrigatório.")
        @Positive(message = "O ID do pedido deve ser positivo.")
        Long pedidoId,

        @NotBlank(message = "A mensagem da notificação é obrigatória.")
        @Size(max = 500, message = "A mensagem deve ter no máximo 500 caracteres.")
        String mensagem

) {
}
