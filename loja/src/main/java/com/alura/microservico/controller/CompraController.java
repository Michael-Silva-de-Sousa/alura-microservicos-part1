package com.alura.microservico.controller;

import com.alura.microservico.dto.CompraDTO;
import com.alura.microservico.model.Compra;
import com.alura.microservico.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public Compra realizaCompra(@RequestBody CompraDTO compraDTO){
        return compraService.realizaCompra(compraDTO);
    }
}
