package com.gabriel.vendas.vendas_api.service;

import com.gabriel.vendas.vendas_api.controller.response.VendedorResponse;
import com.gabriel.vendas.vendas_api.domain.TipoPeriodo;
import com.gabriel.vendas.vendas_api.domain.Venda;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import com.gabriel.vendas.vendas_api.exception.DataInvalidaException;
import com.gabriel.vendas.vendas_api.factory.PeriodoStrategyFactory;
import com.gabriel.vendas.vendas_api.mapper.VendedorMapper;
import com.gabriel.vendas.vendas_api.repository.VendaRepository;
import com.gabriel.vendas.vendas_api.repository.VendedorRepository;
import com.gabriel.vendas.vendas_api.strategy.MediaCalculadoraStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendedorServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendedorMapper vendedorMapper;

    @Mock
    private PeriodoStrategyFactory strategyFactory;

    @Mock
    private MediaCalculadoraStrategy strategy;

    @InjectMocks
    private VendedorService vendedorService;

    @Test
    void deveListarVendedoresComMediaCorretamente() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        Vendedor vendedor = Vendedor.builder().id(1L).nome("Carlos Silva").build();
        Venda venda = Venda.builder().valor(new BigDecimal("1500.00")).build();

        when(strategyFactory.getStrategy(inicio, fim)).thenReturn(strategy);
        when(strategy.getTipo()).thenReturn(TipoPeriodo.DIARIA);
        when(vendedorRepository.findAll()).thenReturn(List.of(vendedor));
        when(vendaRepository.findByVendedorAndDataVendaBetween(vendedor, inicio, fim)).thenReturn(List.of(venda));
        when(strategy.calcular(List.of(venda), inicio, fim)).thenReturn(new BigDecimal("50.00"));
        when(vendedorMapper.toResponse(vendedor, 1L, new BigDecimal("50.00"), TipoPeriodo.DIARIA))
                .thenReturn(new VendedorResponse(1L, "Carlos Silva", 1L, new BigDecimal("50.00"), TipoPeriodo.DIARIA));

        List<VendedorResponse> resultado = vendedorService.listar(inicio, fim);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Carlos Silva");
        assertThat(resultado.get(0).getTipoPeriodo()).isEqualTo(TipoPeriodo.DIARIA);
        assertThat(resultado.get(0).getMediaPeriodo()).isEqualByComparingTo(new BigDecimal("50.00"));
    }

    @Test
    void deveLancarExcecao_quandoDataInicioDepoisDeFim() {
        LocalDate inicio = LocalDate.of(2026, 12, 1);
        LocalDate fim = LocalDate.of(2026, 1, 1);

        assertThatThrownBy(() -> vendedorService.listar(inicio, fim))
                .isInstanceOf(DataInvalidaException.class)
                .hasMessageContaining("início não pode ser posterior");
    }

}