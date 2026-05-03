package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MediaCalculadoraStrategy {

    BigDecimal calcular(List<Venda> vendas, LocalDate inicio, LocalDate fim);
    TipoPeriodo getTipo();
}
