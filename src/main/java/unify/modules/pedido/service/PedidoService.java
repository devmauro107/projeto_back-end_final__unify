package unify.modules.pedido.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unify.exception.ResourceNotFoundException;
import unify.modules.cliente.model.Cliente;
import unify.modules.cliente.repository.ClienteRepository;
import unify.modules.pedido.dto.PedidoRequest;
import unify.modules.pedido.dto.PedidoResponse;
import unify.modules.pedido.model.Pedido;
import unify.modules.pedido.model.StatusPedido;
import unify.modules.pedido.repository.PedidoRepository;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private  final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Cria um novo pedido.
     */
    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + request.clienteId()));

        Pedido pedido = new Pedido();
        pedido.setProduto(request.produto());
        pedido.setDescricao(request.descricao());
        pedido.setQuantidade(request.quantidade());
        pedido.setDataEntrega(request.dataEntrega());
        pedido.setValorTotal(request.valorTotal());

        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setCliente(cliente);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return new PedidoResponse(pedidoSalvo);
    }
    /**
     * Busca um pedido pelo ID.
     */
    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        return new PedidoResponse(pedido);
    }

    /**
     * Lista todos os pedidos, com opção de filtrar por status.
     */
    @Transactional(readOnly = true)
    public List<PedidoResponse> listar(StatusPedido status) {
        List <Pedido> pedidos;

        if (status != null) {
            pedidos = pedidoRepository.findByStatus(status);
        } else {
            pedidos = pedidoRepository.findAll();
        }

        return pedidos.
                stream()
                .map(PedidoResponse::new)
                .toList();
    }

    /**
     * Atualiza o status de um pedido.
     */
    @Transactional
    public PedidoResponse atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        pedido.setStatus(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return new PedidoResponse(pedidoAtualizado);
    }

    /**
     * Atualiza um pedido existente.
     */
    @Transactional
    public PedidoResponse atualizar(Long id, PedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + request.clienteId()));

        pedido.setProduto(request.produto());
        pedido.setDescricao(request.descricao());
        pedido.setQuantidade(request.quantidade());
        pedido.setDataEntrega(request.dataEntrega());
        pedido.setValorTotal(request.valorTotal());
        pedido.setCliente(cliente);

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

            return new PedidoResponse(pedidoAtualizado);
    }
}
