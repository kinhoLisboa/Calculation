package com.orderCalculation.calculation.controller;

import com.orderCalculation.calculation.handler.APIException;
import com.orderCalculation.calculation.request.DetailedPayment;
import com.orderCalculation.calculation.request.OrderRequest;
import com.orderCalculation.calculation.request.PaymentCalculationRequest;
import com.orderCalculation.calculation.response.PurchaseResultResponse;
import com.orderCalculation.calculation.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatePaymentControllerTest {

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private CalculatePaymentController calculatePaymentController;

    @Test
    void testCalculateProportionalPayment_Success() throws UnsupportedEncodingException {

        OrderRequest pedido1 = new OrderRequest("Kinho", new BigDecimal("40"));
        OrderRequest pedido2 = new OrderRequest("Joao", new BigDecimal("50"));
        PaymentCalculationRequest request = new PaymentCalculationRequest(Arrays.asList(pedido1, pedido2), new BigDecimal("10"), new BigDecimal("20"));

        Map<String, BigDecimal> valoresPorComprador = new HashMap<>();
        valoresPorComprador.put("Kinho", new BigDecimal("40"));
        valoresPorComprador.put("Joao", new BigDecimal("50"));
        PurchaseResultResponse expectedResult = new PurchaseResultResponse(Arrays.asList(
                new DetailedPayment("Kinho", new BigDecimal("36.36"), "mocked_link_for_Kinho"),
                new DetailedPayment("Joao", new BigDecimal("45.45"), "mocked_link_for_Joao")), new BigDecimal("81.81"));
        when(calculationService.calculateProportionalPayment(request)).thenReturn(expectedResult);

        ResponseEntity<PurchaseResultResponse> responseEntity = calculatePaymentController.calculateProportionalPayment(request);

        assertEquals(expectedResult, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testCalculateProportionalPayment_ValidationFailure() throws UnsupportedEncodingException {

        OrderRequest pedido1 = new OrderRequest("Kinho", new BigDecimal("40"));
        OrderRequest pedido2 = new OrderRequest("Joao", new BigDecimal("50"));
        PaymentCalculationRequest request = new PaymentCalculationRequest(Arrays.asList(pedido1, pedido2), new BigDecimal("10"), new BigDecimal("20"));

        when(calculatePaymentController.calculateProportionalPayment(request))
                .thenThrow(APIException.build(HttpStatus.NOT_FOUND, "Não foi possível realizar o cálculo!"));


        APIException exception = assertThrows(APIException.class, () -> {
            calculatePaymentController.calculateProportionalPayment(request);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertEquals("Não foi possível realizar o cálculo!", exception.getMessage());
    }
}