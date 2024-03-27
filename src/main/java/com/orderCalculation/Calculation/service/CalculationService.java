package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.DetailedPayment;
import com.orderCalculation.Calculation.request.OrderRequest;
import com.orderCalculation.Calculation.request.PaymentCalculationRequest;
import com.orderCalculation.Calculation.response.PurchaseResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CalculationService {

    private Calculate calculate;

    public PurchaseResultResponse calculateProportionalPayment(PaymentCalculationRequest request) throws UnsupportedEncodingException {
        // Aqui, criamos um objeto Map<String, BigDecimal> com os valores dos pedidos
        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        for (OrderRequest pedido : request.pedidoRequests()) {
            BigDecimal valorAtual = valoresPorComprador.getOrDefault(pedido.comprador(), BigDecimal.ZERO);
            BigDecimal novoValor = valorAtual.add(pedido.valor());
            valoresPorComprador.put(pedido.comprador(), novoValor);
        }

        // Chamamos o método calculatePurchaseResult da classe Calculate com os valores calculados
        PurchaseResultResponse purchaseResult = calculate.calculatePurchaseResult(valoresPorComprador, request.entrega(), request.descontoTotal());

        return purchaseResult;
    }
}
