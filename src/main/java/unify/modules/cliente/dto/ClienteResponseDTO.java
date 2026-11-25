
package unify.modules.cliente.dto;

import unify.modules.cliente.model.Cliente;

/**
 * DTO responsável por retornar os dados do cliente
 * nas respostas da API.
 */
public record ClienteResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email,
        String endereco
) {

    /**
     * Converte uma entidade Cliente em um DTO.
     * Assim, não precisamos retornar diretamente a entidade do banco.
     */
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getEndereco()
        );
    }
}
