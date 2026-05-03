package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MediaDiariaStrategy implements MediaCalculadoraStrategy{

    @Override
    public BigDecimal calcular(List<Venda> vendas, LocalDate inicio, LocalDate fim) {

        Map<LocalDate, List<Venda>> porDia = vendas.stream()
                .collect(Collectors.groupingBy(Venda::getDataVenda));

        BigDecimal somaTicketsMedios = porDia.values().stream()
                .map(vendasDoDia -> {
                    BigDecimal soma = vendasDoDia.stream()
                            .map(Venda::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return soma.divide(
                            BigDecimal.valueOf(vendasDoDia.size()), 2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return somaTicketsMedios.divide(
                BigDecimal.valueOf(porDia.size()), 2, RoundingMode.HALF_UP);
    }

    @Override
    public TipoPeriodo getTipo() {
        return TipoPeriodo.DIARIA;
    }
}
