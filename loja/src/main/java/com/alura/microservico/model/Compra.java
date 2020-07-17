package com.alura.microservico.model;

import com.alura.microservico.enuns.CompraStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private Integer tempoDePedido;
    private String enderecoDestino;
    private Long voucher;
    private LocalDate dataParaEntrega;
    @Enumerated(EnumType.STRING)
    private CompraStatus status;
}
