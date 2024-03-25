package com.orderCalculation.Calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcularPagamentoController {

    @Autowired
    CalculoService calculoService;

    @PostMapping("/calcularPagamento")
    public ResponseEntity<ResultadoCompraResponse> calcularPagamentoProporcional(@RequestBody RequisicaoCalculoPagamentoRequest request) {
        ResultadoCompraResponse resultado = calculoService.calcularPagamentoProporcional(request);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
