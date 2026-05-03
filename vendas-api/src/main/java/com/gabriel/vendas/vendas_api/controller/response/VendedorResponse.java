package com.gabriel.vendas.vendas_api.controller.response;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendedorResponse {

    private Long id;
    private String nome;
    private Long totalVendas;
    private BigDecimal mediaPeriodo;
    private TipoPeriodo tipoPeriodo;
}
