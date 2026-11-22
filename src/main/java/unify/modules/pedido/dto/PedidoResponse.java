package unify.modules.pedido.dto;

import unify.modules.pedido.model.Pedido;
import unify.modules.pedido.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponse
    (
        Long id,
        String produto,
        String descricao,
        Integer quantidade,
        LocalDate dataEntrega,
        StatusPedido status,
        BigDecimal valorTotal,
        Long clienteId,
        String clienteNome
    ) {

    public PedidoResponse(Pedido pedido) {
        this(
            pedido.getId(),
            pedido.getProduto(),
            pedido.getDescricao(),
            pedido.getQuantidade(),
            pedido.getDataEntrega(),
            pedido.getStatus(),
            pedido.getValorTotal(),
            pedido.getCliente().getId(),
            pedido.getCliente().getNome()
        );
    }
}
