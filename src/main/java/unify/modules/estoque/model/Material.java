package unify.modules.estoque.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_materiais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer quantidadeAtual = 0;

    @Column(nullable = false)
    private Integer quantidadeMinima = 0;
}
