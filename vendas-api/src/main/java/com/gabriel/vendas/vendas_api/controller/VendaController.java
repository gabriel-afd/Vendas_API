package com.gabriel.vendas.vendas_api.controller;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendaRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendaResponse;
import com.gabriel.vendas.vendas_api.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponse> criar(@RequestBody @Valid CriarVendaRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vendaService.criar(request));
    }
}
