package unify.modules.pedido.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unify.modules.pedido.dto.PedidoRequest;
import unify.modules.pedido.dto.PedidoResponse;
import unify.modules.pedido.model.StatusPedido;
import unify.modules.pedido.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class PedidoController {

    /**
     * Injeta as dependências do controller.
     */
    private final PedidoService pedidoService;
    private final GenericResponseService responseBuilder;

    public PedidoController(PedidoService pedidoService, GenericResponseService responseBuilder) {
        this.pedidoService = pedidoService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Cria um novo pedido.
     */
    @PostMapping
    @Operation(summary = "Criar um novo pedido")
    public ResponseEntity<PedidoResponse> criar(@RequestBody @Valid PedidoRequest request) {
        PedidoResponse response = pedidoService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Busca um pedido por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar um pedido por ID")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        PedidoResponse response = pedidoService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os pedidos, com opção de filtrar por status.
     */
    @GetMapping
    @Operation(summary = "Listar todos os pedidos ou filtrar por status")
    public ResponseEntity<List<PedidoResponse>> listar(@RequestParam(required = false) StatusPedido status) {
        List<PedidoResponse> response = pedidoService.listar(status);

        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza o status de um pedido.
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar o status de um pedido")
    public ResponseEntity<PedidoResponse> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        PedidoResponse response = pedidoService.atualizarStatus(id, status);

        return ResponseEntity.ok(response);
    }
}
