package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;
import java.util.List;

public record PaymentCalculationRequest(
        List<OrderRequest> pedidoRequests,
        BigDecimal entrega,
        BigDecimal descontoTotal
) {
}
