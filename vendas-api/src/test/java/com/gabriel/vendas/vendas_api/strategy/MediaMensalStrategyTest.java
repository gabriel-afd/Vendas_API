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
    void deveCalcularMediaMensalCorretamente() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 4, 30); // 4 meses

        List<Venda> vendas = List.of(
                vendaCom(new BigDecimal("2000.00")),
                vendaCom(new BigDecimal("2000.00"))
        );

        BigDecimal media = strategy.calcular(vendas, inicio, fim);

        assertThat(media).isEqualByComparingTo(new BigDecimal("1000.00"));
    }

    @Test
    void deveRetornarTipoMensal() {
        assertThat(strategy.getTipo()).isEqualTo(TipoPeriodo.MENSAL);
    }

    private Venda vendaCom(BigDecimal valor) {
        Venda venda = new Venda();
        venda.setValor(valor);
        return venda;
    }

}