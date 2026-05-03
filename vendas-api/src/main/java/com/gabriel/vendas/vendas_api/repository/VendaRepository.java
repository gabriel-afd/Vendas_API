package com.gabriel.vendas.vendas_api.repository;

import com.gabriel.vendas.vendas_api.domain.Venda;
import com.gabriel.vendas.vendas_api.domain.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByVendedorId(Long vendedorId);
    List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);
    List<Venda> findByVendedorAndDataVendaBetween(Vendedor vendedor, LocalDate inicio, LocalDate fim);

}
