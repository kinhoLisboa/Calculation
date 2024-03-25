package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.RequisicaoCalculoPagamentoRequest;
import com.orderCalculation.Calculation.response.ResultadoCompraResponse;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class CalculoService {
    public ResultadoCompraResponse calcularPagamentoProporcional(RequisicaoCalculoPagamentoRequest request) throws UnsupportedEncodingException {
        return Calculo.getResultadoCompra(request.pedidoRequests(), request.entrega(), request.descontoTotal());
    }
}
