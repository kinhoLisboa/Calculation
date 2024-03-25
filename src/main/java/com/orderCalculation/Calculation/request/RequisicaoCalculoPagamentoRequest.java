package com.orderCalculation.Calculation.request;

import java.math.BigDecimal;
import java.util.List;

public record RequisicaoCalculoPagamentoRequest(
        List<PedidoRequest> pedidoRequests,
        BigDecimal entrega,
        BigDecimal descontoTotal
) {
}
