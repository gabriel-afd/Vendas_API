package com.gabriel.vendas.vendas_api.repository;

import com.gabriel.vendas.vendas_api.domain.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
