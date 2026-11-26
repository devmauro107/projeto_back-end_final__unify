package unify.modules.financeiro.docs;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import unify.modules.financeiro.dto.SaldoResponse;
import unify.modules.financeiro.model.SituacaoPagamento;

import java.util.List;

@Tag(name = "Financeiro", description = "Saldo e situação de pagamento dos pedidos")
public interface FinaceiroControllerDocs {


    @Operation(
            summary = "Consultar saldo pendente de um pedido",
            description = "Retorna o valor total, o total pago, o saldo pendente e a situação (PAGO ou PENDENTE)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resumo financeiro do pedido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    ResponseEntity<SaldoResponse> consultarSaldo(@PathVariable("pedidoId") Long pedidoId);

    @Operation(
            summary = "Listar pedidos por situação financeira",
            description = """
                    Use `situacao=PAGO` para pedidos com saldo zero e `situacao=PENDENTE` para pedidos com saldo a pagar.
                    Os valores aceitos são exatamente `PAGO` e `PENDENTE`, em maiúsculas.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos (pode ser vazia)")
    })
    ResponseEntity<List<SaldoResponse>> listarPorSituacao(
            @RequestParam("situacao") SituacaoPagamento situacao);
}
