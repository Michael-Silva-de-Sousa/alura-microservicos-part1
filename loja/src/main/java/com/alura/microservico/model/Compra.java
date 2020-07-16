package com.alura.microservico.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Compra {
    private Long pedidoId;
    private Integer tempoDePedido;
    private String enderecoDestino;
}
