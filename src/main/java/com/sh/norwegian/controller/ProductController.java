package com.sh.norwegian.controller;

import com.sh.norwegian.payload.order.OrderRequest;
import com.sh.norwegian.payload.order.OrderResponse;
import com.sh.norwegian.payload.product.ProductPricesResponse;
import com.sh.norwegian.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private OrderResponse orderDetails;


    @GetMapping("/{productId}/prices")
    public ResponseEntity<List<ProductPricesResponse>> getListOfProductPrices(@PathVariable(name = "productId", required = true) Long productId) {
        return ResponseEntity.ok(productService.getListOfProductPrices(productId));
    }

    @GetMapping("/{productId}/order-details")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable(name = "productId", required = true) Long productId, @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(productService.getOrderDetails(productId, orderRequest));
    }

}
