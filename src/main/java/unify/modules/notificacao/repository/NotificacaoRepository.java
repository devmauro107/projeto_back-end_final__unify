
package unify.modules.notificacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unify.modules.notificacao.model.Notificacao;

import java.util.List;

/**
 * Repository responsável pelo acesso
 * ao banco de dados das notificações.
 */
@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    /**
     * Busca todas as notificações relacionadas a um pedido.
     * Os registros são organizados da mais recente para a mais antiga.
     */
    List<Notificacao> findByPedido_IdOrderByDataEnvioDesc(Long pedidoId);

}
