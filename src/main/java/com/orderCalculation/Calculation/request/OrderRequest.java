package com.orderCalculation.calculation.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderRequest(
        @NotBlank
        String comprador,
        @NotNull
        BigDecimal valor
) {
}