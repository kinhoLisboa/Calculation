package com.orderCalculation.Calculation.financeiro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
@Component
public class Pagamento {
        @Value("${picpay.base.url}")
        private String picpayBaseUrl;

        @Value("${picpay.callback.url}")
        private String callbackUrl;

        @Value("${picpay.seller.token}")
        private String sellerToken;

        public String generatePaymentLink(BigDecimal valor, String comprador) throws UnsupportedEncodingException {
            String referenceId = UUID.randomUUID().toString();

            String returnUrlEncoded = URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8.toString());
            String referenceIdEncoded = URLEncoder.encode(referenceId, StandardCharsets.UTF_8.toString());

            return picpayBaseUrl +
                    "?referenceId=" + referenceIdEncoded +
                    "&callbackUrl=" + returnUrlEncoded +
                    "&value=" + valor.toString() +
                    "&buyer=" + comprador +
                    "&sellerToken=" + sellerToken;
    }
}
