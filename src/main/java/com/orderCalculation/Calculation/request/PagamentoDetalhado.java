package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;

public record PagamentoDetalhado(
        String comprador,
        BigDecimal valorPago,
        String linkPagamento
) {}