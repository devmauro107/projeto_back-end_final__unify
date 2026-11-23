package unify.modules.financeiro.dto;

import unify.modules.financeiro.model.SituacaoPagamento;

import java.math.BigDecimal;


public record SaldoResponse(
        Long pedidoId,
        BigDecimal valorTotal,
        BigDecimal totalPago,
        BigDecimal saldoPendente,
        SituacaoPagamento situacao
) {
}