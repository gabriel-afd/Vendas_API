package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MediaMensalStrategy implements MediaCalculadoraStrategy{
    @Override
    public BigDecimal calcular(List<Venda> vendas, LocalDate inicio, LocalDate fim) {

        Map<YearMonth, List<Venda>> porMes = vendas.stream()
                .collect(Collectors.groupingBy(venda ->
                        YearMonth.from(venda.getDataVenda())));

        BigDecimal somaTicketsMedios = porMes.values().stream()
                .map(vendasDoMes -> {
                    BigDecimal soma = vendasDoMes.stream()
                            .map(Venda::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return soma.divide(
                            BigDecimal.valueOf(vendasDoMes.size()), 2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return somaTicketsMedios.divide(
                BigDecimal.valueOf(porMes.size()), 2, RoundingMode.HALF_UP);
    }

    @Override
    public TipoPeriodo getTipo() {
        return TipoPeriodo.MENSAL;
    }
}
