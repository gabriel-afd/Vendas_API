package com.gabriel.vendas.vendas_api.factory;

import com.gabriel.vendas.vendas_api.strategy.MediaCalculadoraStrategy;
import com.gabriel.vendas.vendas_api.strategy.MediaDiariaStrategy;
import com.gabriel.vendas.vendas_api.strategy.MediaMensalStrategy;
import com.gabriel.vendas.vendas_api.strategy.MediaSemanalStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class PeriodoStrategyFactory {

    private final MediaDiariaStrategy diariaStrategy;
    private final MediaSemanalStrategy semanalStrategy;
    private final MediaMensalStrategy mensalStrategy;

    public MediaCalculadoraStrategy getStrategy(LocalDate inicio, LocalDate fim){

        long dias = ChronoUnit.DAYS.between(inicio, fim);
        if (dias <= 31)  return diariaStrategy;
        if (dias <= 90)  return semanalStrategy;

        return mensalStrategy;
    }
}
