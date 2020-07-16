package br.com.alura.microservice.fornecedor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.microservice.fornecedor.model.InfoFornecedor;
import br.com.alura.microservice.fornecedor.service.InfoService;

@Slf4j
@RestController
@RequestMapping("/info")
public class InfoController {
	
	@Autowired
	private InfoService infoService;


	@RequestMapping("/{estado}")
	public InfoFornecedor getInfoPorEstado(@PathVariable String estado) {
		log.info("Recebido pedido de informação do fornecedor de {},", estado);
		return infoService.getInfoPorEstado(estado);
	}
}
