package com.gabriel.vendas.vendas_api.service;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendedorRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendedorResponse;
import com.gabriel.vendas.vendas_api.domain.Venda;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import com.gabriel.vendas.vendas_api.exception.DataInvalidaException;
import com.gabriel.vendas.vendas_api.factory.PeriodoStrategyFactory;
import com.gabriel.vendas.vendas_api.mapper.VendedorMapper;
import com.gabriel.vendas.vendas_api.repository.VendaRepository;
import com.gabriel.vendas.vendas_api.repository.VendedorRepository;
import com.gabriel.vendas.vendas_api.strategy.MediaCalculadoraStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository vendedorRepository;
    private final VendaRepository vendaRepository;
    private final VendedorMapper vendedorMapper;
    private final PeriodoStrategyFactory strategyFactory;

    @Transactional
    public VendedorResponse criar(CriarVendedorRequest request){

        Vendedor vendedorSalvo = vendedorRepository.save(vendedorMapper.toEntity(request));

        return vendedorMapper.toResponse(vendedorSalvo,0L, BigDecimal.ZERO, null);
    }

    public List<VendedorResponse> listar(LocalDate inicio, LocalDate fim){

        if (inicio.isAfter(fim)){
            throw new DataInvalidaException("Data de início não pode ser posterior à data de fim");
        }

        MediaCalculadoraStrategy strategy = strategyFactory.getStrategy(inicio, fim);

        return vendedorRepository.findAll().stream()
                .map(vendedor -> {
                    List<Venda> vendas = vendaRepository.findByVendedorAndDataVendaBetween(vendedor, inicio, fim);

                    BigDecimal media = vendas.isEmpty() ? BigDecimal.ZERO : strategy.calcular(vendas, inicio, fim);

                    return vendedorMapper.toResponse(vendedor, (long) vendas.size(), media, strategy.getTipo());
                })
                .toList();
    }
}
