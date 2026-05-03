package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MediaSemanalStrategyTest {

    private final MediaSemanalStrategy strategy = new MediaSemanalStrategy();

    @Test
    void deveCalcularTicketMedioSemanalCorretamente() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        // semana 1: vendas de 500 e 1000 → ticket médio = 750
        // semana 3: venda de 600 → ticket médio = 600
        // média entre semanas = (750 + 600) / 2 = 675
        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("500.00"), LocalDate.of(2026, 1, 5)),
                vendaCom(new BigDecimal("1000.00"), LocalDate.of(2026, 1, 5)),
                vendaCom(new BigDecimal("600.00"), LocalDate.of(2026, 1, 19))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("675.00"));
    }

    @Test
    void deveRetornarTicketMedioUnico_quandoTodasVendasNaMesmaSemana() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        // duas vendas na mesma semana → ticket médio = (300 + 700) / 2 = 500
        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("300.00"), LocalDate.of(2026, 1, 5)),
                vendaCom(new BigDecimal("700.00"), LocalDate.of(2026, 1, 6))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("500.00"));
    }

    @Test
    void deveRetornarTipoSemanal() {
        assertThat(strategy.getTipo()).isEqualTo(TipoPeriodo.SEMANAL);
    }

    private Venda vendaCom(BigDecimal valor, LocalDate data) {
        Venda venda = new Venda();
        venda.setValor(valor);
        venda.setDataVenda(data);
        return venda;
    }
}