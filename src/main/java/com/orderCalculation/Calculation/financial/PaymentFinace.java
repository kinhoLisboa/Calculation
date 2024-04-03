package com.orderCalculation.calculation.financial;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public interface PaymentFinace {
    public String generatePaymentLink(BigDecimal valor, String comprador) throws UnsupportedEncodingException ;

    }
