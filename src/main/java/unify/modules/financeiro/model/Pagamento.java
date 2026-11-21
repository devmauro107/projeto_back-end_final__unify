package unify.modules.financeiro.model;

import jakarta.persistence.*;
import lombok.*;
import unify.modules.pedido.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;
}
