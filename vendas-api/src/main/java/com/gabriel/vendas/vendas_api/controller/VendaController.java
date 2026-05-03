package com.gabriel.vendas.vendas_api.controller;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendaRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendaResponse;
import com.gabriel.vendas.vendas_api.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<VendaResponse>> listar() {
        return ResponseEntity.ok(vendaService.listar());
    }
}
