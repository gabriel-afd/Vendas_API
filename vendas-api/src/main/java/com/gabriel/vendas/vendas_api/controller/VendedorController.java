package com.gabriel.vendas.vendas_api.controller;

import com.gabriel.vendas.vendas_api.controller.request.CriarVendedorRequest;
import com.gabriel.vendas.vendas_api.controller.response.VendedorResponse;
import com.gabriel.vendas.vendas_api.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendedores")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorService vendedorService;

    @PostMapping
    public ResponseEntity<VendedorResponse> criar(@RequestBody @Valid CriarVendedorRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vendedorService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponse>> listar(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(vendedorService.listar(inicio, fim));
    }

}
