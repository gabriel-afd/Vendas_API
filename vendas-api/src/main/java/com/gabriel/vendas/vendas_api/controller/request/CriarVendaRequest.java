package com.gabriel.vendas.vendas_api.controller.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriarVendaRequest {

    @NotNull(message = "Data da venda é obrigatória")
    private LocalDate dataVenda;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Id do vendedor é obrigatório")
    private Long vendedorId;
}
