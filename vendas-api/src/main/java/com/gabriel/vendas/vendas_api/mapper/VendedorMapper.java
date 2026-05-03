package com.gabriel.vendas.vendas_api.mapper;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendedorRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendedorResponse;
import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VendedorMapper {

    public Vendedor toEntity(CriarVendedorRequest request){

        return Vendedor.builder()
                .nome(request.getNome())
                .build();
    }

    public VendedorResponse toResponse(Vendedor vendedor, Long totalVendas, BigDecimal media, TipoPeriodo tipoPeriodo) {
        return VendedorResponse.builder()
                .id(vendedor.getId())
                .nome(vendedor.getNome())
                .totalVendas(totalVendas)
                .mediaPeriodo(media)
                .tipoPeriodo(tipoPeriodo)
                .build();
    }
}
