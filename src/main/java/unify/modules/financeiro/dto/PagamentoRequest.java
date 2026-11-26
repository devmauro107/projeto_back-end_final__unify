package unify.modules.financeiro.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import unify.modules.financeiro.model.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoRequest(

        @NotNull(message = "O id do pedido é obrigatório")
        Long pedidoId,

        @NotNull(message = "O valor pago é obrigatório")
        @Positive(message = "O valor pago deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "O valor pago deve ter no máximo 2 casas decimais")
        BigDecimal valorPago,

        LocalDate dataPagamento,

        @NotNull(message = "A forma de pagamento é obrigatória")
        FormaPagamento formaPagamento
) {
}