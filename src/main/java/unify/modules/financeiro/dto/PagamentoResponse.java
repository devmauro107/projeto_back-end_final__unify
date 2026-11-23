package unify.modules.financeiro.dto;

import unify.modules.financeiro.model.FormaPagamento;
import unify.modules.financeiro.model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representação de um pagamento devolvida pela API.
 * Nunca expõe a entidade Pagamento diretamente.
 */
public record PagamentoResponse(
        Long id,
        Long pedidoId,
        BigDecimal valorPago,
        LocalDate dataPagamento,
        FormaPagamento formaPagamento
) {

    public static PagamentoResponse from(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getValorPago(),
                pagamento.getDataPagamento(),
                pagamento.getFormaPagamento()
        );
    }
}