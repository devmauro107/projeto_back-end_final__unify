package unify.modules.notificacao.model;

import jakarta.persistence.*;
import lombok.*;
import unify.modules.pedido.model.Pedido;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notificacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, length = 500)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataEnvio = LocalDateTime.now();
}
