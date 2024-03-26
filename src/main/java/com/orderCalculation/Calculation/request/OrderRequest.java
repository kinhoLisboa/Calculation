package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;

public record OrderRequest(
        String comprador,
        BigDecimal valor
) {
}