package com.orderCalculation.Calculation.response;


import com.orderCalculation.Calculation.request.DetailedPayment;

import java.math.BigDecimal;
import java.util.List;

public record PurchaseResultResponse(
        List<DetailedPayment> pagamentosDetalhados,
        BigDecimal totalComDesconto
) {
}

