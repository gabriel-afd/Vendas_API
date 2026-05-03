package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MediaSemanalStrategy implements MediaCalculadoraStrategy{

    @Override
    public BigDecimal calcular(List<Venda> vendas, LocalDate inicio, LocalDate fim) {

        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        Map<String, List<Venda>> porSemana = vendas.stream()
                .collect(Collectors.groupingBy(venda -> {
                    int ano = venda.getDataVenda().getYear();
                    int semana = venda.getDataVenda().get(weekFields.weekOfWeekBasedYear());
                    return ano + "-S" + semana;
                }));

        BigDecimal somaTicketsMedios = porSemana.values().stream()
                .map(vendasDaSemana -> {
                    BigDecimal soma = vendasDaSemana.stream()
                            .map(Venda::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return soma.divide(
                            BigDecimal.valueOf(vendasDaSemana.size()), 2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return somaTicketsMedios.divide(
                BigDecimal.valueOf(porSemana.size()), 2, RoundingMode.HALF_UP);
    }

    @Override
    public TipoPeriodo getTipo() {
        return TipoPeriodo.SEMANAL;
    }
}
