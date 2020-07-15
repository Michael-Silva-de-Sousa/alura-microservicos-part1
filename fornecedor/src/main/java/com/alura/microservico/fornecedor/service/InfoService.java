package com.alura.microservico.fornecedor.service;

import com.alura.microservico.fornecedor.model.InfoFornecedor;
import com.alura.microservico.fornecedor.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Autowired
    private InfoRepository infoRepository;

    public InfoFornecedor getInfoPorEstado(String estado){
        return infoRepository.findByEstado(estado);
    }
}
