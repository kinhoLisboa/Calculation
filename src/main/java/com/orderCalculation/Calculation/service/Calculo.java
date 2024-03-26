package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.financeiro.Pagamento;
import com.orderCalculation.Calculation.request.PagamentoDetalhado;
import com.orderCalculation.Calculation.request.PedidoRequest;
import com.orderCalculation.Calculation.response.ResultadoCompraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class Calculo {
    @Autowired
    private Pagamento pagamento;

    public ResultadoCompraResponse calcularResultadoCompra(List<PedidoRequest> pedidoRequests, BigDecimal entrega, BigDecimal descontoTotal) throws UnsupportedEncodingException {
        Map<String, BigDecimal> valoresPorComprador = calcularValoresPorComprador(pedidoRequests);
        BigDecimal total = calcularTotal(pedidoRequests);
        BigDecimal totalComDesconto = calcularTotalComDesconto(total, descontoTotal);
        BigDecimal descontoProporcional = calcularDescontoProporcional(pedidoRequests, descontoTotal, total);
        List<PagamentoDetalhado> pagamentosDetalhados = calcularPagamentosDetalhados(valoresPorComprador, entrega, totalComDesconto);
        return new ResultadoCompraResponse(pagamentosDetalhados, totalComDesconto);
    }

    private Map<String, BigDecimal> calcularValoresPorComprador(List<PedidoRequest> pedidoRequests) {
        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        for (PedidoRequest pedidoRequest : pedidoRequests) {
            BigDecimal valorAtual = valoresPorComprador.getOrDefault(pedidoRequest.comprador(), BigDecimal.ZERO);
            BigDecimal novoValor = valorAtual.add(pedidoRequest.valor());
            valoresPorComprador.put(pedidoRequest.comprador(), novoValor);
        }
        return valoresPorComprador;
    }

    private BigDecimal calcularTotal(List<PedidoRequest> pedidoRequests) {
        return pedidoRequests.stream()
                .map(PedidoRequest::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularTotalComDesconto(BigDecimal total, BigDecimal descontoTotal) {
        return total.subtract(descontoTotal);
    }

    private BigDecimal calcularDescontoProporcional(List<PedidoRequest> pedidoRequests, BigDecimal descontoTotal, BigDecimal total) {
        return pedidoRequests.stream()
                .map(PedidoRequest::valor)
                .map(valor -> valor.divide(total, 10, RoundingMode.HALF_EVEN).multiply(descontoTotal))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<PagamentoDetalhado> calcularPagamentosDetalhados(Map<String, BigDecimal> valoresPorComprador, BigDecimal entrega, BigDecimal totalComDesconto) throws UnsupportedEncodingException {
        List<PagamentoDetalhado> pagamentosDetalhados = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : valoresPorComprador.entrySet()) {
            String comprador = entry.getKey();
            BigDecimal valorPedido = entry.getValue();

            BigDecimal valorDescontoIndividual = valorPedido.divide(totalComDesconto, 10, RoundingMode.HALF_EVEN).multiply(entrega);
            BigDecimal totalIndividual = valorPedido.subtract(valorDescontoIndividual).add(entrega);

            String linkPagamento = pagamento.generatePaymentLink(totalIndividual.setScale(2, RoundingMode.HALF_EVEN), comprador);
            pagamentosDetalhados.add(new PagamentoDetalhado(comprador, totalIndividual.setScale(2, RoundingMode.HALF_EVEN), linkPagamento));
        }
        return pagamentosDetalhados;
    }



}
