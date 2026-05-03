package com.gabriel.vendas.vendas_api.service;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendaRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendaResponse;
import com.gabriel.vendas.vendas_api.domain.Venda;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import com.gabriel.vendas.vendas_api.exception.VendedorNotFoundException;
import com.gabriel.vendas.vendas_api.mapper.VendaMapper;
import com.gabriel.vendas.vendas_api.repository.VendaRepository;
import com.gabriel.vendas.vendas_api.repository.VendedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private VendaMapper vendaMapper;

    @InjectMocks
    private VendaService vendaService;

    @Test
    void deveCriarVendaComSucesso() {
        Vendedor vendedor = Vendedor.builder().id(1L).nome("Carlos Silva").build();
        CriarVendaRequest request = new CriarVendaRequest(LocalDate.of(2026, 5, 1), new BigDecimal("1500.00"), 1L);
        Venda venda = Venda.builder().id(1L).dataVenda(request.getDataVenda()).valor(request.getValor()).vendedor(vendedor).build();
        VendaResponse response = new VendaResponse(1L, request.getDataVenda(), request.getValor(), 1L, "Carlos Silva");

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendaMapper.toEntity(request, vendedor)).thenReturn(venda);
        when(vendaRepository.save(venda)).thenReturn(venda);
        when(vendaMapper.toResponse(venda)).thenReturn(response);

        VendaResponse resultado = vendaService.criar(request);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNomeVendedor()).isEqualTo("Carlos Silva");
        assertThat(resultado.getValor()).isEqualByComparingTo(new BigDecimal("1500.00"));
        verify(vendaRepository).save(venda);
    }

    @Test
    void deveLancarExcecao_quandoVendedorNaoEncontrado() {
        CriarVendaRequest request = new CriarVendaRequest(LocalDate.of(2026, 5, 1), new BigDecimal("1500.00"), 999L);

        when(vendedorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vendaService.criar(request))
                .isInstanceOf(VendedorNotFoundException.class)
                .hasMessageContaining("999");
    }

}