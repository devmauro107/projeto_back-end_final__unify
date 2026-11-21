package unify.modules.estoque.model;

import jakarta.persistence.*;
import lombok.*;
import unify.modules.pedido.model.Pedido;

@Entity
@Table(name = "tb_pedido_materiais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer quantidadeUtilizada;
}
