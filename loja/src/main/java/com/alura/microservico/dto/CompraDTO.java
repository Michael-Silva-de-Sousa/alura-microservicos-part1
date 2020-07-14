package com.alura.microservico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CompraDTO {
    private List<ItemCompraDTO> itens;
    private EnderecoDTO endereco;
}
