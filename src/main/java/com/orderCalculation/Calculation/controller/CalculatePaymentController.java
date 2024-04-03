package com.orderCalculation.calculation.controller;

import com.orderCalculation.calculation.request.PaymentCalculationRequest;
import com.orderCalculation.calculation.response.PurchaseResultResponse;
import com.orderCalculation.calculation.service.CalculationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
@RestController
@RequestMapping("/calcularPagamento")
@AllArgsConstructor
@Log4j2
public class CalculatePaymentController {


   private CalculationService calculationService;

    @PostMapping
    public ResponseEntity<PurchaseResultResponse> calculateProportionalPayment(@Valid @RequestBody PaymentCalculationRequest request) throws UnsupportedEncodingException {
        log.info( "[Start] CalculatePaymentController - calculateProportionalPayment");
        PurchaseResultResponse purchaseResult = calculationService.calculateProportionalPayment(request);
        log.info( "[Finish] CalculatePaymentController - calculateProportionalPayment");
        return new ResponseEntity<>(purchaseResult, HttpStatus.OK);
    }
}
