package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class MediaDiariaStrategy implements MediaCalculadoraStrategy{

    @Override
    public BigDecimal calcular(List<Venda> vendas, LocalDate inicio, LocalDate fim) {

        long dias = ChronoUnit.DAYS.between(inicio, fim) + 1;
        BigDecimal total = vendas.stream()
                .map(Venda::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
    }

    @Override
    public TipoPeriodo getTipo() {
        return TipoPeriodo.DIARIA;
    }
}
