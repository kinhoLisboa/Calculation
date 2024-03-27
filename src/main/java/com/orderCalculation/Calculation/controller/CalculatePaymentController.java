package com.orderCalculation.Calculation.controller;

import com.orderCalculation.Calculation.request.DetailedPayment;
import com.orderCalculation.Calculation.request.PaymentCalculationRequest;
import com.orderCalculation.Calculation.response.PurchaseResultResponse;
import com.orderCalculation.Calculation.service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/calcularPagamento")
@AllArgsConstructor
public class CalculatePaymentController {


   private CalculationService calculationService;

    @PostMapping
    public ResponseEntity<PurchaseResultResponse> calculateProportionalPayment(@RequestBody PaymentCalculationRequest request) throws UnsupportedEncodingException {
        PurchaseResultResponse purchaseResult = calculationService.calculateProportionalPayment(request);
        return new ResponseEntity<>(purchaseResult, HttpStatus.OK);
    }
}
