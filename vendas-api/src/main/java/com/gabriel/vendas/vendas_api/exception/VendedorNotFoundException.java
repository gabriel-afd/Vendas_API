package com.gabriel.vendas.vendas_api.exception;

public class VendedorNotFoundException extends RuntimeException {
    public VendedorNotFoundException(Long id) {
        super("Vendedor não encontrado com id: " + id);
    }
}
