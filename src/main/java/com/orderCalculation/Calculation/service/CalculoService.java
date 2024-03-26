package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.RequisicaoCalculoPagamentoRequest;
import com.orderCalculation.Calculation.response.ResultadoCompraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class CalculoService {
    @Autowired
    private Calculo calculo;

    public ResultadoCompraResponse calcularPagamentoProporcional(RequisicaoCalculoPagamentoRequest request) throws UnsupportedEncodingException {
        ResultadoCompraResponse resultadoCompra = calculo.calcularResultadoCompra(request.pedidoRequests(), request.entrega(), request.descontoTotal());
        return resultadoCompra;
    }
}
