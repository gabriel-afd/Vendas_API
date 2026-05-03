package com.gabriel.vendas.vendas_api.service;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendaRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendaResponse;
import com.gabriel.vendas.vendas_api.domain.Venda;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import com.gabriel.vendas.vendas_api.exception.VendedorNotFoundException;
import com.gabriel.vendas.vendas_api.mapper.VendaMapper;
import com.gabriel.vendas.vendas_api.repository.VendaRepository;
import com.gabriel.vendas.vendas_api.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendedorRepository vendedorRepository;
    private final VendaMapper vendaMapper;

    @Transactional
    public VendaResponse criar(CriarVendaRequest request){

        Vendedor vendedor = vendedorRepository.findById(request.getVendedorId())
                .orElseThrow(() -> new VendedorNotFoundException(request.getVendedorId()));

        Venda vendaSalva = vendaRepository.save(vendaMapper.toEntity(request, vendedor));

        return vendaMapper.toResponse(vendaSalva);
    }

    public List<VendaResponse> listar() {

        return vendaRepository.findAll().stream()
                .map(vendaMapper::toResponse)
                .toList();
    }
}
