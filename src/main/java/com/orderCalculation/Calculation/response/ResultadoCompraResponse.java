package com.orderCalculation.Calculation.response;

import com.ti.demo.request.PagamentoDetalhado;

import java.math.BigDecimal;
import java.util.List;

public record ResultadoCompraResponse(
        List<PagamentoDetalhado> pagamentosDetalhados,
        BigDecimal totalComDesconto
) {}

