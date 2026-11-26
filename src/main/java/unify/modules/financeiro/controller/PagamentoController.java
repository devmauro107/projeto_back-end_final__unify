package unify.modules.financeiro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unify.modules.financeiro.docs.PagamentoControllerDocs;
import unify.modules.financeiro.dto.PagamentoRequest;
import unify.modules.financeiro.dto.PagamentoResponse;
import unify.modules.financeiro.dto.RegistroPagamentoResponse;
import unify.modules.financeiro.service.FinanceiroService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamentos", description = "Registro e consulta de pagamentos dos pedidos")
public class PagamentoController implements PagamentoControllerDocs {

    private final FinanceiroService financeiroService;

    public PagamentoController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @PostMapping
    public ResponseEntity<RegistroPagamentoResponse> registrar(@Valid @RequestBody PagamentoRequest request) {
        RegistroPagamentoResponse response = financeiroService.registrarPagamento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponse>> listarPorPedido(
            @RequestParam("pedidoId") Long pedidoId) {
        return ResponseEntity.ok(financeiroService.listarPagamentos(pedidoId));
    }
}