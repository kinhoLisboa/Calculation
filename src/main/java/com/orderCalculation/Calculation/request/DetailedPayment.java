package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;

public record DetailedPayment(
        String comprador,
        BigDecimal valorPago,
        String linkPagamento
) {}