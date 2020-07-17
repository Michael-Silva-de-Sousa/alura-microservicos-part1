package com.alura.microservico.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDTO {
    private Long numero;
    private LocalDate previsaoParaEntrega;
}
