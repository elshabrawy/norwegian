package com.sh.norwegian.payload.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    private Long customerId;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

}
