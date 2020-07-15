package com.alura.microservico.service;

import com.alura.microservico.dto.CompraDTO;
import com.alura.microservico.dto.InfoFornecedorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CompraService {

    public void realizaCompra(CompraDTO compraDTO){
        RestTemplate client = new RestTemplate();

        //Chamada síncrona
        ResponseEntity<InfoFornecedorDTO> exchange =
                client.exchange("http://localhost:8081/info/" + compraDTO.getEndereco().getEstado(),
                HttpMethod.GET,null, InfoFornecedorDTO.class);

        log.info(exchange.getBody().getEndereco());
    }
}
