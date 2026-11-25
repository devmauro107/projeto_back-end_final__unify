
package unify.modules.cliente.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unify.exception.ResourceNotFoundException;
import unify.modules.cliente.dto.ClienteRequestDTO;
import unify.modules.cliente.dto.ClienteResponseDTO;
import unify.modules.cliente.model.Cliente;
import unify.modules.cliente.repository.ClienteRepository;

import java.util.List;

/**
 * Service responsável pelas regras de negócio
 * relacionadas ao cadastro e gerenciamento de clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Cadastra um novo cliente.
     */
    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequestDTO dados) {

        Cliente cliente = new Cliente();

        cliente.setNome(dados.nome());
        cliente.setTelefone(dados.telefone());
        cliente.setEmail(dados.email());
        cliente.setEndereco(dados.endereco());

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteResponseDTO.fromEntity(clienteSalvo);
    }

    /**
     * Retorna todos os clientes cadastrados.
     */
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {

        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    /**
     * Busca um cliente específico pelo ID.
     */
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {

        Cliente cliente = buscarCliente(id);

        return ClienteResponseDTO.fromEntity(cliente);
    }

    /**
     * Atualiza os dados de um cliente existente.
     */
    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dados) {

        Cliente cliente = buscarCliente(id);

        cliente.setNome(dados.nome());
        cliente.setTelefone(dados.telefone());
        cliente.setEmail(dados.email());
        cliente.setEndereco(dados.endereco());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return ClienteResponseDTO.fromEntity(clienteAtualizado);
    }

    /**
     * Exclui um cliente pelo ID.
     */
    @Transactional
    public void excluir(Long id) {

        Cliente cliente = buscarCliente(id);

        clienteRepository.delete(cliente);
    }

    /**
     * Método auxiliar para localizar um cliente.
     * Caso não exista, lança uma exceção de recurso não encontrado.
     */
    private Cliente buscarCliente(Long id) {

        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com o ID: " + id
                ));
    }
}
