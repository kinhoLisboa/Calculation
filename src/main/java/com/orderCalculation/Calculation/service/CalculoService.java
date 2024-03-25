package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.RequisicaoCalculoPagamentoRequest;
import com.orderCalculation.Calculation.response.ResultadoCompraResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculoService {
    public ResultadoCompraResponse calcularPagamentoProporcional(RequisicaoCalculoPagamentoRequest request) {
        return com.ti.demo.service.Calculo.getResultadoCompra(request.pedidoRequests(), request.entrega(), request.descontoTotal());
    }
}
