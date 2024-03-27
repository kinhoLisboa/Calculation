package com.orderCalculation.Calculation.service;

import com.orderCalculation.Calculation.request.DetailedPayment;
import com.orderCalculation.Calculation.request.OrderRequest;
import com.orderCalculation.Calculation.request.PaymentCalculationRequest;
import com.orderCalculation.Calculation.response.PurchaseResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CalculationServiceTest {
    @Mock
    private Calculate calculate;

    @InjectMocks
    private CalculationService calculationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateProportionalPayment() throws UnsupportedEncodingException {

        OrderRequest pedido1 = new OrderRequest("Ranelho", new BigDecimal("40"));
        OrderRequest pedido2 = new OrderRequest("Joao", new BigDecimal("50"));
        PaymentCalculationRequest request = new PaymentCalculationRequest(Arrays.asList(pedido1, pedido2), new BigDecimal("10"), new BigDecimal("20"));

        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        valoresPorComprador.put("Ranelho", new BigDecimal("40"));
        valoresPorComprador.put("Joao", new BigDecimal("50"));
        PurchaseResultResponse expectedResponse = new PurchaseResultResponse(Arrays.asList(
                new DetailedPayment("Ranelho", new BigDecimal("36.36"), "mocked_link_for_Ranelho"),
                new DetailedPayment("Joao", new BigDecimal("45.45"), "mocked_link_for_Joao")), new BigDecimal("81.81"));
        when(calculate.calculatePurchaseResult(valoresPorComprador, new BigDecimal("10"), new BigDecimal("20"))).thenReturn(expectedResponse);

        PurchaseResultResponse actualResponse = calculationService.calculateProportionalPayment(request);

        assertEquals(expectedResponse, actualResponse);
    }

}