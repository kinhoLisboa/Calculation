package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.financial.Payment;
import com.orderCalculation.Calculation.request.DetailedPayment;
import com.orderCalculation.Calculation.request.OrderRequest;
import com.orderCalculation.Calculation.response.PurchaseResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@AllArgsConstructor
public class Calculate {

    private Payment payment;

    public PurchaseResultResponse calculatePurchaseResult(List<OrderRequest> pedidoRequests, BigDecimal entrega, BigDecimal descontoTotal) throws UnsupportedEncodingException {
        Map<String, BigDecimal> valoresPorComprador = calculateValuesPerBuyer(pedidoRequests);
        BigDecimal total = calculateTotal(pedidoRequests);
        BigDecimal totalComDesconto = calculateTotalWithDiscount(total, descontoTotal);
        BigDecimal descontoProporcional = calculateProportionalDiscount(pedidoRequests, descontoTotal, total);
        List<DetailedPayment> pagamentosDetalhados = calculateDetailedPayments(valoresPorComprador, entrega, totalComDesconto);
        return new PurchaseResultResponse(pagamentosDetalhados, totalComDesconto);
    }

    private Map<String, BigDecimal> calculateValuesPerBuyer(List<OrderRequest> pedidoRequests) {
        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        for (OrderRequest pedidoRequest : pedidoRequests) {
            BigDecimal valorAtual = valoresPorComprador.getOrDefault(pedidoRequest.comprador(), BigDecimal.ZERO);
            BigDecimal novoValor = valorAtual.add(pedidoRequest.valor());
            valoresPorComprador.put(pedidoRequest.comprador(), novoValor);
        }
        return valoresPorComprador;
    }

    private BigDecimal calculateTotal(List<OrderRequest> pedidoRequests) {
        return pedidoRequests.stream()
                .map(OrderRequest::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalWithDiscount(BigDecimal total, BigDecimal descontoTotal) {
        return total.subtract(descontoTotal);
    }

    private BigDecimal calculateProportionalDiscount(List<OrderRequest> pedidoRequests, BigDecimal descontoTotal, BigDecimal total) {
        return pedidoRequests.stream()
                .map(OrderRequest::valor)
                .map(valor -> valor.divide(total, 10, RoundingMode.HALF_EVEN).multiply(descontoTotal))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<DetailedPayment> calculateDetailedPayments(Map<String, BigDecimal> valoresPorComprador, BigDecimal entrega, BigDecimal totalComDesconto) throws UnsupportedEncodingException {
        List<DetailedPayment> detailedPayments = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : valoresPorComprador.entrySet()) {
            String comprador = entry.getKey();
            BigDecimal valorPedido = entry.getValue();

            BigDecimal valorDescontoIndividual = valorPedido.divide(totalComDesconto, 10, RoundingMode.HALF_EVEN).multiply(entrega);
            BigDecimal totalIndividual = valorPedido.subtract(valorDescontoIndividual).add(entrega);

            String linkPagamento = payment.generatePaymentLink(totalIndividual.setScale(2, RoundingMode.HALF_EVEN), comprador);
            detailedPayments.add(new DetailedPayment(comprador, totalIndividual.setScale(2, RoundingMode.HALF_EVEN), linkPagamento));
        }
        return detailedPayments;
    }



}
