package com.alura.microservico.service;

import com.alura.microservico.client.FornecedorClient;
import com.alura.microservico.client.TransportadorClient;
import com.alura.microservico.dto.*;
import com.alura.microservico.enuns.CompraStatus;
import com.alura.microservico.model.Compra;
import com.alura.microservico.repository.CompraRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.sound.sampled.Line;
import java.time.LocalDate;

@Slf4j
@Service
public class CompraService {

    //@Autowired
    //private RestTemplate client;

    @Autowired
    private FornecedorClient fornecedorClient;
    @Autowired
    private TransportadorClient transportadorClient;
    @Autowired
    private CompraRepository compraRepository;

    //@HystrixCommand(threadPoolKey = "getByIdThreadPool")
    public Compra getById(Long id) {
        return compraRepository.findById(id).orElse(Compra.builder().build());
    }

    //@HystrixCommand(fallbackMethod = "realizaCompraFallback",
    //threadPoolKey = "realizaCompraThreadPool")
    public Compra realizaCompra(CompraDTO compraDTO){

        Compra compra = Compra
                            .builder()
                            .status(CompraStatus.RECEBIDO)
                            .build();
        compraRepository.save(compra);

        log.info("Buscando informações do fornecedor de {}", compraDTO.getEndereco().getEstado());
        InfoFornecedorDTO fornecedorDTO = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado());

        log.info("Realizando pedido");
        InfoPedidoDTO pedidoDTO = fornecedorClient.realizaPedido(compraDTO.getItens());
        compra.builder().status(CompraStatus.REALIZADO);
        compraRepository.save(compra);

        InfoEtregaDTO infoEtregaDTO = InfoEtregaDTO
                .builder()
                .id(pedidoDTO.getId())
                .dataParaEntrega(LocalDate.now().plusDays(pedidoDTO.getTempoDePreparo()))
                .enderecoOrigem(fornecedorDTO.getEndereco())
                .enderecoDestino(compraDTO.getEndereco().toString())
                .build();

        VoucherDTO voucherDTO = transportadorClient.reservaEntrega(infoEtregaDTO);
        compra.builder().status(CompraStatus.RESERVA_ENTREGA_REALIZADA);
        compraRepository.save(compra);

        Compra
            .builder()
            .pedidoId(pedidoDTO.getId())
            .tempoDePedido(pedidoDTO.getTempoDePreparo())
            .enderecoDestino(compraDTO.getEndereco().toString())
            .dataParaEntrega(voucherDTO.getPrevisaoParaEntrega())
            .voucher(voucherDTO.getNumero())
            .build();
        compraRepository.save(compra);

        return compra;
        /**
        //Chamada síncrona
        ResponseEntity<InfoFornecedorDTO> exchange =
                client.exchange("http://fornecedor/info/" + compraDTO.getEndereco().getEstado(),
                HttpMethod.GET,null, InfoFornecedorDTO.class);

        log.info(exchange.getBody().getEndereco());
         */
    }

    private Compra realizaCompraFallback(CompraDTO compraDTO){
        return Compra
                    .builder()
                    .enderecoDestino(compraDTO.getEndereco().toString())
                    .build();
    }
}
