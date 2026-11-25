
package unify.modules.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unify.modules.cliente.model.Cliente;

/**
 * Repository responsável pela comunicação
 * entre a aplicação e o banco de dados de clientes.
 *
 * O JpaRepository já disponibiliza operações básicas
 * como salvar, buscar, listar e excluir registros.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
