
package unify.modules.notificacao.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unify.modules.notificacao.dto.NotificacaoRequestDTO;
import unify.modules.notificacao.dto.NotificacaoResponseDTO;
import unify.modules.notificacao.service.NotificacaoService;

import java.net.URI;
import java.util.List;

/**
 * Controller responsável pelos endpoints REST
 * do módulo de notificações.
 */
@RestController
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    /**
     * Registra uma nova notificação.
     */
    @PostMapping("/notificacoes")
    public ResponseEntity<NotificacaoResponseDTO> registrar(
            @Valid @RequestBody NotificacaoRequestDTO dados) {

        NotificacaoResponseDTO notificacao =
                notificacaoService.registrar(dados);

        return ResponseEntity
                .created(URI.create("/notificacoes/" + notificacao.id()))
                .body(notificacao);
    }

    /**
     * Consulta todas as notificações de um pedido.
     */
    @GetMapping("/pedidos/{id}/notificacoes")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarPorPedido(
            @PathVariable("id") Long pedidoId) {

        return ResponseEntity.ok(
                notificacaoService.listarPorPedido(pedidoId)
        );
    }
}
