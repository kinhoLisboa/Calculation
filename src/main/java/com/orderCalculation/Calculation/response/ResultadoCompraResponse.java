package com.orderCalculation.Calculation.response;


import com.orderCalculation.Calculation.request.PagamentoDetalhado;

import java.math.BigDecimal;
import java.util.List;

public record ResultadoCompraResponse(
        List<PagamentoDetalhado> pagamentosDetalhados,
        BigDecimal totalComDesconto
) {}

