package com.orderCalculation.calculation.service;

import com.orderCalculation.calculation.handler.APIException;
import com.orderCalculation.calculation.request.OrderRequest;
import com.orderCalculation.calculation.request.PaymentCalculationRequest;
import com.orderCalculation.calculation.response.PurchaseResultResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Log4j2
public class CalculationService {

    private Calculate calculate;

    public PurchaseResultResponse calculateProportionalPayment(PaymentCalculationRequest request) throws UnsupportedEncodingException {
        log.info("[Start] CalculationService - calculateProportionalPayment");
        request.validateCalculate();
        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        for (OrderRequest pedido : request.pedidoRequests()) {
            BigDecimal valorAtual = valoresPorComprador.getOrDefault(pedido.comprador(), BigDecimal.ZERO);
            BigDecimal novoValor = valorAtual.add(pedido.valor());
            valoresPorComprador.put(pedido.comprador(), novoValor);
        }

        PurchaseResultResponse purchaseResult = calculate.calculatePurchaseResult(valoresPorComprador, request.entrega(), request.descontoTotal());
        if (purchaseResult == null || purchaseResult.totalComDesconto().compareTo(BigDecimal.ZERO) <= 0) {
            // Se o resultado for inválido, lance uma exceção
            throw APIException.build(HttpStatus.NOT_FOUND,"Erro no calculo !");
        }
        log.info("[Finish] CalculationService - calculateProportionalPayment");
        return purchaseResult;
    }


}
