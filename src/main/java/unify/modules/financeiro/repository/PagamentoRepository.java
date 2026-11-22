package unify.modules.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unify.modules.financeiro.model.Pagamento;
import unify.modules.pedido.model.Pedido;

import java.math.BigDecimal;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {


    List<Pagamento> findByPedidoIdOrderByDataPagamentoAscIdAsc(Long pedidoId);


    @Query("SELECT COALESCE(SUM(p.valorPago), 0) FROM Pagamento p WHERE p.pedido.id = :pedidoId")
    BigDecimal somarPagamentosDoPedido(@Param("pedidoId") Long pedidoId);


    @Query("""
            SELECT p FROM Pedido p
            WHERE p.valorTotal <= (SELECT COALESCE(SUM(pg.valorPago), 0) FROM Pagamento pg WHERE pg.pedido = p)
            ORDER BY p.id
            """)
    List<Pedido> findPedidosPagos();


    @Query("""
            SELECT p FROM Pedido p
            WHERE p.valorTotal > (SELECT COALESCE(SUM(pg.valorPago), 0) FROM Pagamento pg WHERE pg.pedido = p)
            ORDER BY p.id
            """)
    List<Pedido> findPedidosPendentes();
}