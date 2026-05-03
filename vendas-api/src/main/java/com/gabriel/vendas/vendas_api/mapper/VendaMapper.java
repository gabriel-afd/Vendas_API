package com.gabriel.vendas.vendas_api.mapper;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendaRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendaResponse;
import com.gabriel.vendas.vendas_api.domain.Venda;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import org.springframework.stereotype.Component;

@Component
public class VendaMapper {

    public Venda toEntity(CriarVendaRequest request, Vendedor vendedor){

        return Venda.builder()
                .dataVenda(request.getDataVenda())
                .valor(request.getValor())
                .vendedor(vendedor)
                .build();
    }

    public VendaResponse toResponse(Venda venda){
        return VendaResponse.builder()
                .id(venda.getId())
                .dataVenda(venda.getDataVenda())
                .valor(venda.getValor())
                .vendedorId(venda.getVendedor().getId())
                .nomeVendedor(venda.getVendedor().getNome())
                .build();
    }
}
