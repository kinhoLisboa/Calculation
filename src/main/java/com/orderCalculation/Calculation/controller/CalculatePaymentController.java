package com.orderCalculation.Calculation.controller;

import com.orderCalculation.Calculation.request.PaymentCalculationRequest;
import com.orderCalculation.Calculation.response.PurchaseResultResponse;
import com.orderCalculation.Calculation.service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/calcularPagamento")
@AllArgsConstructor
public class CalculatePaymentController {


   private CalculationService calculationService;

    @PostMapping
    public ResponseEntity<PurchaseResultResponse> calculateProportionalPayment(@RequestBody PaymentCalculationRequest request) throws UnsupportedEncodingException {
        PurchaseResultResponse resultado = calculationService.calculateProportionalPayment(request);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
