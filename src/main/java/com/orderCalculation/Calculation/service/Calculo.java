package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.PagamentoDetalhado;
import com.orderCalculation.Calculation.request.PedidoRequest;
import com.orderCalculation.Calculation.response.ResultadoCompraResponse;
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
public class Calculo {
    private static Pagamento pagamento;

    public static ResultadoCompraResponse getResultadoCompra(List<PedidoRequest> pedidoRequests, BigDecimal entrega, BigDecimal descontoTotal) throws UnsupportedEncodingException {
        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;


        for (PedidoRequest pedidoRequest : pedidoRequests) {
            total = total.add(pedidoRequest.valor());
            valoresPorComprador.put(pedidoRequest.comprador(), valoresPorComprador.getOrDefault(pedidoRequest.comprador(), BigDecimal.ZERO).add(pedidoRequest.valor()));
        }

        BigDecimal totalComDesconto = total.subtract(descontoTotal);
        BigDecimal descontoProporcional = BigDecimal.ZERO;
        List<PagamentoDetalhado> pagamentosDetalhados = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : valoresPorComprador.entrySet()) {
            String comprador = entry.getKey();
            BigDecimal valorPedido = entry.getValue();

            BigDecimal valorDescontoIndividual = valorPedido.divide(total, 10, RoundingMode.HALF_EVEN).multiply(descontoTotal);
            BigDecimal valorComDesconto = valorPedido.subtract(valorDescontoIndividual);
            descontoProporcional = descontoProporcional.add(valorDescontoIndividual);

            BigDecimal valorEntregaIndividual = valorComDesconto.divide(totalComDesconto, 10, RoundingMode.HALF_EVEN).multiply(entrega);
            BigDecimal totalIndividual = valorComDesconto.add(valorEntregaIndividual);


            String linkPagamento = pagamento.gerarLinkPagamento(totalIndividual.setScale(2, RoundingMode.HALF_EVEN), comprador);

            pagamentosDetalhados.add(new PagamentoDetalhado(comprador, totalIndividual.setScale(2, RoundingMode.HALF_EVEN), linkPagamento));
        }

        return new ResultadoCompraResponse(pagamentosDetalhados, totalComDesconto);
    }



}
