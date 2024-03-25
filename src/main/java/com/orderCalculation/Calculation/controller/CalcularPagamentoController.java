package com.orderCalculation.Calculation.controller;

import com.orderCalculation.Calculation.request.RequisicaoCalculoPagamentoRequest;
import com.orderCalculation.Calculation.response.ResultadoCompraResponse;
import com.orderCalculation.Calculation.service.CalculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/calcularPagamento")
public class CalcularPagamentoController {

    @Autowired
    CalculoService calculoService;

    @PostMapping
    public ResponseEntity<ResultadoCompraResponse> calcularPagamentoProporcional(@RequestBody RequisicaoCalculoPagamentoRequest request) throws UnsupportedEncodingException {
        ResultadoCompraResponse resultado = calculoService.calcularPagamentoProporcional(request);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
