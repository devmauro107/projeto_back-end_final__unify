package unify.modules.financeiro.dto;

import unify.modules.financeiro.model.SituacaoPagamento;

import java.math.BigDecimal;

/**
 * Resposta de POST /pagamentos (201 Created):
 * o pagamento registrado e a situação do pedido após o registro.
 */
public record RegistroPagamentoResponse(
        PagamentoResponse pagamento,
        BigDecimal saldoPendente,
        SituacaoPagamento situacao
) {
}