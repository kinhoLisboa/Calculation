package com.orderCalculation.calculation.service;
import com.orderCalculation.calculation.financial.PaymentFinace;
import com.orderCalculation.calculation.request.DetailedPayment;

import com.orderCalculation.calculation.response.PurchaseResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
@AllArgsConstructor
public class Calculate {

    private PaymentFinace paymentFinace;

    public PurchaseResultResponse calculatePurchaseResult(Map<String, BigDecimal> valoresPorComprador, BigDecimal entrega, BigDecimal totalComDesconto) throws UnsupportedEncodingException {
        List<DetailedPayment> detailedPayments = calcularPagamentosDetalhados(valoresPorComprador, entrega, totalComDesconto);
        BigDecimal totalFinal = calcularTotalFinal(valoresPorComprador, entrega, totalComDesconto);
        return new PurchaseResultResponse(detailedPayments, totalFinal);
    }

    private List<DetailedPayment> calcularPagamentosDetalhados(Map<String, BigDecimal> valoresPorComprador, BigDecimal entrega, BigDecimal totalComDesconto) throws UnsupportedEncodingException {
        List<DetailedPayment> detailedPayments = new ArrayList<>();
        BigDecimal totalPedidosComEntrega = calcularTotalPedidosComEntrega(valoresPorComprador, entrega);

        for (Map.Entry<String, BigDecimal> entry : valoresPorComprador.entrySet()) {
            String comprador = entry.getKey();
            BigDecimal valorPedido = entry.getValue();
            BigDecimal totalIndividual = calcularTotalIndividual(valorPedido, entrega, totalPedidosComEntrega, totalComDesconto);
            String linkPagamento = paymentFinace.generatePaymentLink(totalIndividual.setScale(2, RoundingMode.HALF_EVEN), comprador);
            detailedPayments.add(new DetailedPayment(comprador, totalIndividual.setScale(2, RoundingMode.HALF_EVEN), linkPagamento));
        }
        return detailedPayments;
    }

    private BigDecimal calcularTotalPedidosComEntrega(Map<String, BigDecimal> valoresPorComprador, BigDecimal entrega) {
        BigDecimal totalPedidos = valoresPorComprador.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPedidos.add(entrega.multiply(BigDecimal.valueOf(valoresPorComprador.size())));
    }

    private BigDecimal calcularTotalIndividual(BigDecimal valorPedido, BigDecimal entrega, BigDecimal totalPedidosComEntrega, BigDecimal totalComDesconto) {
        BigDecimal proporcaoPedido = valorPedido.divide(totalPedidosComEntrega, 10, RoundingMode.HALF_EVEN);
        BigDecimal valorComDesconto = valorPedido.subtract(totalComDesconto.multiply(proporcaoPedido));
        return valorComDesconto.add(entrega.multiply(proporcaoPedido));
    }

    private BigDecimal calcularTotalFinal(Map<String, BigDecimal> valoresPorComprador, BigDecimal entrega, BigDecimal totalComDesconto) {
        BigDecimal totalPedidosComEntrega = calcularTotalPedidosComEntrega(valoresPorComprador, entrega);
        return totalPedidosComEntrega.subtract(totalComDesconto);
    }
}