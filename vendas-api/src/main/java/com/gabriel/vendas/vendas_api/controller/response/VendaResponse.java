package com.gabriel.vendas.vendas_api.controller.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaResponse {

    private Long id;
    private LocalDate dataVenda;
    private BigDecimal valor;
    private Long vendedorId;
    private String nomeVendedor;
}
