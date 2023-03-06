package com.sh.norwegian.payload.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Builder
//@SessionScope

public class OrderResponse {
    private BigDecimal totalPrice;
    private int numberOfCartons;
    private int numberOfUnits;
    private String  productName;
    private String customerName;

}
