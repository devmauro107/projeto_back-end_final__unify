
package unify.modules.cliente.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unify.modules.cliente.dto.ClienteRequestDTO;
import unify.modules.cliente.dto.ClienteResponseDTO;
import unify.modules.cliente.service.ClienteService;

import java.net.URI;
import java.util.List;

/**
 * Controller responsável por disponibilizar
 * os endpoints REST para gerenciamento de clientes.
 */
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Cadastra um novo cliente.
     */
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(
            @Valid @RequestBody ClienteRequestDTO dados) {

        ClienteResponseDTO cliente = clienteService.cadastrar(dados);

        return ResponseEntity
                .created(URI.create("/clientes/" + cliente.id()))
                .body(cliente);
    }

    /**
     * Lista todos os clientes cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {

        return ResponseEntity.ok(clienteService.listarTodos());
    }

    /**
     * Busca um cliente pelo seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    /**
     * Atualiza os dados de um cliente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dados) {

        return ResponseEntity.ok(clienteService.atualizar(id, dados));
    }

    /**
     * Exclui um cliente pelo ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        clienteService.excluir(id);
    }
}
