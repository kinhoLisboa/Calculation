package com.orderCalculation.calculation.request;

import java.math.BigDecimal;

public record DetailedPayment(
        String comprador,
        BigDecimal valorPago,
        String linkPagamento
) {}