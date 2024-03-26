package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.PaymentCalculationRequest;
import com.orderCalculation.Calculation.response.PurchaseResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class CalculationService {

    private Calculate calculate;

    public PurchaseResultResponse calculateProportionalPayment(PaymentCalculationRequest request) throws UnsupportedEncodingException {
        PurchaseResultResponse purchaseResult = calculate.calculatePurchaseResult(request.pedidoRequests(), request.entrega(), request.descontoTotal());
        return purchaseResult;
    }
}
