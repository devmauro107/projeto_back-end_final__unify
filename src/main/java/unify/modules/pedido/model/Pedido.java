package unify.modules.pedido.model;

import jakarta.persistence.*;
import lombok.*;
import unify.modules.cliente.model.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String produto;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Integer quantidade;

    private LocalDate dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.RECEBIDO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
