package com.gabriel.vendas.vendas_api.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriarVendedorRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
}
