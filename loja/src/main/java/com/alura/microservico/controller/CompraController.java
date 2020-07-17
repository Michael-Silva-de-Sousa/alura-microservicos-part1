package com.alura.microservico.controller;

import com.alura.microservico.dto.CompraDTO;
import com.alura.microservico.model.Compra;
import com.alura.microservico.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping("/{id}")
    public Compra getById(@PathVariable("id") Long id){
        return compraService.getById(id);
    }

    @PostMapping
    public Compra realizaCompra(@RequestBody CompraDTO compraDTO){
        return compraService.realizaCompra(compraDTO);
    }
}
