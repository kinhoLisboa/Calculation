package com.orderCalculation.calculation.request;


import com.orderCalculation.calculation.handler.APIException;
import org.springframework.http.HttpStatus;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record PaymentCalculationRequest(

        @NotEmpty
        List<OrderRequest> pedidoRequests,
        @NotNull
        BigDecimal entrega,
        @NotNull
        BigDecimal descontoTotal
) {

        public void validateCalculate() {
                if(pedidoRequests.isEmpty()){
                        throw  APIException.build(HttpStatus.NOT_FOUND,"Os campos não podem ser vazios ou nulos !");
                }if(entrega.byteValue()== 0 || entrega == null){
                        throw APIException.build(HttpStatus.NOT_FOUND,"O campo não pode ser nulo ou de valor 0 !");
                }

        }
}
