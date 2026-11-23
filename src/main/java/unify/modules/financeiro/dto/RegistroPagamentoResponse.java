package unify.modules.financeiro.dto;

import unify.modules.financeiro.model.SituacaoPagamento;

import java.math.BigDecimal;


public record RegistroPagamentoResponse(
        PagamentoResponse pagamento,
        BigDecimal saldoPendente,
        SituacaoPagamento situacao
) {
}