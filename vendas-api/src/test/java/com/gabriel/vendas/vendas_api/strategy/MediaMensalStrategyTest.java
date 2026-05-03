package com.gabriel.vendas.vendas_api.strategy;

import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MediaMensalStrategyTest {

    private final MediaMensalStrategy strategy = new MediaMensalStrategy();

    @Test
    void deveCalcularTicketMedioMensalCorretamente() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 3, 31);

        // janeiro: vendas de 500 e 1000 → ticket médio = 750
        // março: venda de 600 → ticket médio = 600
        // média entre meses = (750 + 600) / 2 = 675
        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("500.00"), LocalDate.of(2026, 1, 10)),
                vendaCom(new BigDecimal("1000.00"), LocalDate.of(2026, 1, 20)),
                vendaCom(new BigDecimal("600.00"), LocalDate.of(2026, 3, 15))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("675.00"));
    }

    @Test
    void deveRetornarTicketMedioUnico_quandoTodasVendasNoMesmoMes() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 3, 31);

        // duas vendas em janeiro → ticket médio = (200 + 800) / 2 = 500
        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("200.00"), LocalDate.of(2026, 1, 5)),
                vendaCom(new BigDecimal("800.00"), LocalDate.of(2026, 1, 25))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("500.00"));
    }

    @Test
    void deveRetornarTipoMensal() {
        assertThat(strategy.getTipo()).isEqualTo(TipoPeriodo.MENSAL);
    }

    private Venda vendaCom(BigDecimal valor, LocalDate data) {
        Venda venda = new Venda();
        venda.setValor(valor);
        venda.setDataVenda(data);
        return venda;
    }
}