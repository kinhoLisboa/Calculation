package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;
import java.util.List;

public record RequisicaoCalculoPagamentoRequest(
        List<com.ti.demo.request.PedidoRequest> pedidoRequests,
        BigDecimal entrega,
        BigDecimal descontoTotal
) {
}
