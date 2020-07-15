package com.alura.microservico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoFornecedorDTO {
    private Long id;
    private String nome;
    private String estado;
    private String endereco;
}
