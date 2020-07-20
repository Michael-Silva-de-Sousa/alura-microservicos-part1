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

        Compra compraSalva = new Compra();
        compraSalva.setStatus(CompraStatus.RECEBIDO);
        compraSalva.setEnderecoDestino(compraDTO.getEndereco().toString());
        compraRepository.save(compraSalva);

        InfoFornecedorDTO fornecedorDTO = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado());
        log.info("Realizando pedido");
        InfoPedidoDTO pedidoDTO = fornecedorClient.realizaPedido(compraDTO.getItens());
        compraSalva.setStatus(CompraStatus.REALIZADO);
        compraSalva.setPedidoId(pedidoDTO.getId());
        compraSalva.setTempoDePedido(pedidoDTO.getTempoDePreparo());
        compraRepository.save(compraSalva);

        InfoEtregaDTO infoEtregaDTO = InfoEtregaDTO
                .builder()
                .id(pedidoDTO.getId())
                .dataParaEntrega(LocalDate.now().plusDays(pedidoDTO.getTempoDePreparo()))
                .enderecoOrigem(fornecedorDTO.getEndereco())
                .enderecoDestino(compraDTO.getEndereco().toString())
                .build();

        VoucherDTO voucherDTO = transportadorClient.reservaEntrega(infoEtregaDTO);
        compraSalva.setStatus(CompraStatus.RESERVA_ENTREGA_REALIZADA);
        compraSalva.setDataParaEntrega(voucherDTO.getPrevisaoParaEntrega());
        compraSalva.setVoucher(voucherDTO.getNumero());
        compraRepository.save(compraSalva);

        return compraSalva;
    }

    private Compra realizaCompraFallback(CompraDTO compraDTO){
        return Compra
                    .builder()
                    .enderecoDestino(compraDTO.getEndereco().toString())
                    .build();
    }
}
