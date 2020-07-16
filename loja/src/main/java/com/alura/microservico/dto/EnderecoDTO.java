package com.alura.microservico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class EnderecoDTO {
    private String rua;
    private Integer numero;
    private String estado;
}
