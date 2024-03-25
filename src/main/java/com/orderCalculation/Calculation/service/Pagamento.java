package com.orderCalculation.Calculation.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Pagamento {
    private static final String PICPAY_BASE_URL = "https://appws.picpay.com/ecommerce/public/payments/";

    static String gerarLinkPagamento(BigDecimal valor, String comprador) throws UnsupportedEncodingException {
        // Lógica para gerar o link de pagamento com base no valor e no comprador
        // Este é apenas um exemplo simplificado


        String sellerToken = "SEU_TOKEN_PICPAY";
        String referenceId = UUID.randomUUID().toString();
        String callbackUrl = "https://seusite.com/callback";


        String returnUrlEncoded = URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8.toString());
        String referenceIdEncoded = URLEncoder.encode(referenceId, StandardCharsets.UTF_8.toString());


        return PICPAY_BASE_URL +
                "?referenceId=" + referenceIdEncoded +
                "&callbackUrl=" + returnUrlEncoded +
                "&value=" + valor.toString() +
                "&buyer=" + comprador +
                "&sellerToken=" + sellerToken;
    }
}
