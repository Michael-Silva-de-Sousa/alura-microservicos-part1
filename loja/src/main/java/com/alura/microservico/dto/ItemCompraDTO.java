package com.alura.microservico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemCompraDTO {
    private Long id;
    private Integer quantidade;
}
