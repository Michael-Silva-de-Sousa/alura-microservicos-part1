package com.alura.microservico.client;

import com.alura.microservico.dto.InfoEtregaDTO;
import com.alura.microservico.dto.VoucherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("transportador")
public interface TransportadorClient {

    @PostMapping(value = "/entrega")
    public VoucherDTO reservaEntrega(InfoEtregaDTO infoEtregaDTO);
}
