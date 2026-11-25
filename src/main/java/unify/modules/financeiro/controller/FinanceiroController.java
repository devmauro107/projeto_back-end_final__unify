package unify.modules.financeiro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unify.modules.financeiro.docs.FinaceiroControllerDocs;
import unify.modules.financeiro.dto.SaldoResponse;
import unify.modules.financeiro.model.SituacaoPagamento;
import unify.modules.financeiro.service.FinanceiroService;

import java.util.List;

@RestController
@RequestMapping("/financeiro")
public class FinanceiroController implements FinaceiroControllerDocs {

    private final FinanceiroService financeiroService;

    public FinanceiroController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @GetMapping("/saldo/{pedidoId}")

    public ResponseEntity<SaldoResponse> consultarSaldo(@PathVariable("pedidoId") Long pedidoId) {
        return ResponseEntity.ok(financeiroService.consultarSaldo(pedidoId));
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<SaldoResponse>> listarPorSituacao(
            @RequestParam("situacao") SituacaoPagamento situacao) {
        return ResponseEntity.ok(financeiroService.listarPedidosPorSituacao(situacao));
    }
}