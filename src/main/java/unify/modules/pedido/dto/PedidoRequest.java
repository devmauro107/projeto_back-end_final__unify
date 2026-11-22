package unify.modules.pedido.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoRequest
    (
        @NotBlank(message = "O nome do produto é obrigatório")
        String produto,

        String descricao,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        LocalDate dataEntrega,

        @NotNull(message = "O valor total é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valorTotal,

        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId
    ) {
}
