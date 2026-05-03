package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MediaDiariaStrategyTest {

    private final MediaDiariaStrategy strategy = new MediaDiariaStrategy();

    @Test
    void deveCalcularTicketMedioDiarioCorretamente() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        // dia 01: duas vendas de 500 e 1000 → ticket médio = 750
        // dia 10: uma venda de 600 → ticket médio = 600
        // média entre os dias = (750 + 600) / 2 = 675
        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("500.00"), LocalDate.of(2026, 1, 1)),
                vendaCom(new BigDecimal("1000.00"), LocalDate.of(2026, 1, 1)),
                vendaCom(new BigDecimal("600.00"), LocalDate.of(2026, 1, 10))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("675.00"));
    }

    @Test
    void deveRetornarTicketMedioUnico_quandoTodasVendasNoMesmoDia() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        // duas vendas no mesmo dia → ticket médio = (400 + 600) / 2 = 500
        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("400.00"), LocalDate.of(2026, 1, 5)),
                vendaCom(new BigDecimal("600.00"), LocalDate.of(2026, 1, 5))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("500.00"));
    }

    @Test
    void deveRetornarTipoDiaria() {
        assertThat(strategy.getTipo()).isEqualTo(TipoPeriodo.DIARIA);
    }

    private Venda vendaCom(BigDecimal valor, LocalDate data) {
        Venda venda = new Venda();
        venda.setValor(valor);
        venda.setDataVenda(data);
        return venda;
    }
}