package com.alura.microservico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EnderecoDTO {
    private String rua;
    private Integer numero;
    private String estado;
}
