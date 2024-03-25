package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;

public record PedidoRequest(
        String comprador,
        BigDecimal valor
) {
}