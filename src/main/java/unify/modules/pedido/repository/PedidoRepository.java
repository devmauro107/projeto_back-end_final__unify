package unify.modules.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unify.modules.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
