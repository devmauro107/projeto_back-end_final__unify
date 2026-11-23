package unify.modules.financeiro.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unify.exception.BusinessException;
import unify.exception.ResourceNotFoundException;
import unify.modules.financeiro.dto.PagamentoRequest;
import unify.modules.financeiro.dto.PagamentoResponse;
import unify.modules.financeiro.dto.RegistroPagamentoResponse;
import unify.modules.financeiro.dto.SaldoResponse;
import unify.modules.financeiro.model.Pagamento;
import unify.modules.financeiro.model.SituacaoPagamento;
import unify.modules.financeiro.repository.PagamentoRepository;
import unify.modules.pedido.model.Pedido;
import unify.modules.pedido.repository.PedidoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Regras de negócio do módulo financeiro.
 */
@Service
public class FinanceiroService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public FinanceiroService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Registra um pagamento para um pedido existente, sem permitir que ultrapasse o saldo.
     */
    @Transactional
    public RegistroPagamentoResponse registrarPagamento(PagamentoRequest request) {
        Pedido pedido = buscarPedido(request.pedidoId());

        BigDecimal saldoAtual = calcularSaldo(pedido);

        if (request.valorPago().compareTo(saldoAtual) > 0) {
            throw new BusinessException(
                    "O valor pago (" + request.valorPago() + ") ultrapassa o saldo pendente do pedido ("
                            + saldoAtual + ")");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValorPago(request.valorPago());
        pagamento.setDataPagamento(request.dataPagamento() != null ? request.dataPagamento() : LocalDate.now());
        pagamento.setFormaPagamento(request.formaPagamento());

        pagamentoRepository.save(pagamento);

        BigDecimal saldoNovo = saldoAtual.subtract(request.valorPago());

        return new RegistroPagamentoResponse(
                PagamentoResponse.from(pagamento),
                saldoNovo,
                definirSituacao(saldoNovo)
        );
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponse> listarPagamentos(Long pedidoId) {
        buscarPedido(pedidoId);

        return pagamentoRepository.findByPedidoIdOrderByDataPagamentoAscIdAsc(pedidoId)
                .stream()
                .map(PagamentoResponse::from)
                .toList();
    }

    /**
     * Retorna o resumo financeiro (total, pago, saldo e situação) de um pedido.
     */
    @Transactional(readOnly = true)
    public SaldoResponse consultarSaldo(Long pedidoId) {
        Pedido pedido = buscarPedido(pedidoId);
        return montarSaldo(pedido);
    }

    /**
     * Lista os pedidos com a situação informada (PAGO ou PENDENTE).
     */
    @Transactional(readOnly = true)
    public List<SaldoResponse> listarPedidosPorSituacao(SituacaoPagamento situacao) {
        List<Pedido> pedidos = switch (situacao) {
            case PAGO -> pagamentoRepository.findPedidosPagos();
            case PENDENTE -> pagamentoRepository.findPedidosPendentes();
        };

        return pedidos.stream()
                .map(this::montarSaldo)
                .toList();
    }

    // ---------- métodos auxiliares ----------

    private Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + pedidoId));
    }

    /**
     * Saldo = valor total do pedido - soma de todos os pagamentos.
     */
    private BigDecimal calcularSaldo(Pedido pedido) {
        BigDecimal totalPago = pagamentoRepository.somarPagamentosDoPedido(pedido.getId());
        return pedido.getValorTotal().subtract(totalPago);
    }

    private SaldoResponse montarSaldo(Pedido pedido) {
        BigDecimal totalPago = pagamentoRepository.somarPagamentosDoPedido(pedido.getId());
        BigDecimal saldo = pedido.getValorTotal().subtract(totalPago);

        return new SaldoResponse(
                pedido.getId(),
                pedido.getValorTotal(),
                totalPago,
                saldo,
                definirSituacao(saldo)
        );
    }

    /**
     * PAGO quando o saldo chega a zero; PENDENTE caso contrário.
     * Usa signum() em vez de equals() para não depender da escala do BigDecimal.
     */
    private SituacaoPagamento definirSituacao(BigDecimal saldo) {
        return saldo.signum() == 0 ? SituacaoPagamento.PAGO : SituacaoPagamento.PENDENTE;
    }
}