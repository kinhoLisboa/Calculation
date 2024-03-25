package com.orderCalculation.Calculation.service;

import org.springframework.stereotype.Component;

@Component
public class Calculo {
    public static ResultadoCompraResponse getResultadoCompra(List<PedidoRequest> pedidoRequests, BigDecimal entrega, BigDecimal descontoTotal) {
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


            String linkPagamento = gerarLinkPagamento(totalIndividual.setScale(2, RoundingMode.HALF_EVEN), comprador);

            pagamentosDetalhados.add(new PagamentoDetalhado(comprador, totalIndividual.setScale(2, RoundingMode.HALF_EVEN), linkPagamento));
        }

        return new ResultadoCompraResponse(pagamentosDetalhados, totalComDesconto);
    }


    private static String gerarLinkPagamento(BigDecimal valor, String comprador) {
        // Lógica para gerar o link de pagamento com base no valor e no comprador
        // Aqui você deve implementar a lógica real para gerar o link de pagamento
        // Este é apenas um exemplo simplificado
        return "https://picpay.me/" + comprador + "/" + valor;
    }
}
