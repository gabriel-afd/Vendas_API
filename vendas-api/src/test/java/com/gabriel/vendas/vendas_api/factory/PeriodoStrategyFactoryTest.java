package com.gabriel.vendas.vendas_api.factory;

import com.gabriel.vendas.vendas_api.strategy.MediaCalculadoraStrategy;
import com.gabriel.vendas.vendas_api.strategy.MediaDiariaStrategy;
import com.gabriel.vendas.vendas_api.strategy.MediaMensalStrategy;
import com.gabriel.vendas.vendas_api.strategy.MediaSemanalStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PeriodoStrategyFactoryTest {

    @Mock
    private MediaDiariaStrategy diariaStrategy;

    @Mock
    private MediaSemanalStrategy semanalStrategy;

    @Mock
    private MediaMensalStrategy mensalStrategy;

    @InjectMocks
    private PeriodoStrategyFactory factory;

    @Test
    void deveRetornarStrategyDiaria_quandoPeriodoAte31Dias() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        MediaCalculadoraStrategy strategy = factory.getStrategy(inicio, fim);

        assertThat(strategy).isInstanceOf(MediaDiariaStrategy.class);
    }

    @Test
    void deveRetornarStrategySemanal_quandoPeriodoEntre32E90Dias() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 3, 31);

        MediaCalculadoraStrategy strategy = factory.getStrategy(inicio, fim);

        assertThat(strategy).isInstanceOf(MediaSemanalStrategy.class);
    }

    @Test
    void deveRetornarStrategyMensal_quandoPeriodoAcimaDe90Dias() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 12, 31);

        MediaCalculadoraStrategy strategy = factory.getStrategy(inicio, fim);

        assertThat(strategy).isInstanceOf(MediaMensalStrategy.class);
    }

}