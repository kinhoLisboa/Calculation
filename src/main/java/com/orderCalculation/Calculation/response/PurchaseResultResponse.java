package com.orderCalculation.calculation.response;


import com.orderCalculation.calculation.request.DetailedPayment;

import java.math.BigDecimal;
import java.util.List;

public record PurchaseResultResponse(
        List<DetailedPayment> pagamentosDetalhados,
        BigDecimal totalComDesconto
) {
}

