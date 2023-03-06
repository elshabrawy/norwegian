package com.sh.norwegian.payload.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProductPricesResponse {
    private String name;
    private int unitsCount;
    private BigDecimal unitsPrice;
}
