package com.alura.microservico.service;

import com.alura.microservico.client.FornecedorClient;
import com.alura.microservico.dto.CompraDTO;
import com.alura.microservico.dto.InfoFornecedorDTO;
import com.alura.microservico.dto.InfoPedidoDTO;
import com.alura.microservico.model.Compra;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CompraService {

    //@Autowired
    //private RestTemplate client;

    @Autowired
    private FornecedorClient fornecedorClient;

    public Compra realizaCompra(CompraDTO compraDTO){

        InfoFornecedorDTO fornecedorDTO = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado());
        InfoPedidoDTO pedidoDTO = fornecedorClient.realizaPedido(compraDTO.getItens());

        log.info(fornecedorDTO.getEndereco());

        return Compra
                .builder()
                .pedidoId(pedidoDTO.getId())
                .tempoDePedido(pedidoDTO.getTempoDePreparo())
                .enderecoDestino(compraDTO.getEndereco().toString())
                .build();

        /**
        //Chamada síncrona
        ResponseEntity<InfoFornecedorDTO> exchange =
                client.exchange("http://fornecedor/info/" + compraDTO.getEndereco().getEstado(),
                HttpMethod.GET,null, InfoFornecedorDTO.class);

        log.info(exchange.getBody().getEndereco());
         */
    }
}
