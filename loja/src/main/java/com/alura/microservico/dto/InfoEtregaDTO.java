package com.alura.microservico.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoEtregaDTO {
    private Long id;
    private LocalDate dataParaEntrega;
    private String enderecoOrigem;
    private String enderecoDestino;
}
