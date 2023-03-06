package com.sh.norwegian.service;

import com.sh.norwegian.exception.ProductNotFoundException;
import com.sh.norwegian.exception.CustomerNotFoundException;
import com.sh.norwegian.model.Cart;
import com.sh.norwegian.model.Customer;
import com.sh.norwegian.model.Product;
import com.sh.norwegian.payload.order.OrderRequest;
import com.sh.norwegian.payload.order.OrderResponse;
import com.sh.norwegian.payload.product.ProductPricesResponse;
import com.sh.norwegian.repository.CartRepository;
import com.sh.norwegian.repository.CustomerRepository;
import com.sh.norwegian.repository.ProductRepository;
import com.sh.norwegian.util.PriceCalculation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@EnableAspectJAutoProxy
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final PriceCalculation priceCalculation;
    private final CustomerRepository customerRepository;

    public List<ProductPricesResponse> getListOfProductPrices(Long productId) {
        return priceCalculation.getProductPricesResponse(getProduct(productId));
    }

    public OrderResponse getOrderDetails(Long productId,OrderRequest orderRequest) throws RuntimeException {
        Product product = getProduct(productId);
        Customer customer = getCustomer(orderRequest.getCustomerId());
        Optional<Cart> productHistory = cartRepository.findByCustomerIdAndProductId(orderRequest.getCustomerId(), productId);
        int totalQuantity = priceCalculation.getTotalQuantity(orderRequest.getQuantity(), product.getNumberOfUnits(), productHistory);
        return OrderResponse.builder()
                .productName(product.getName())
                .numberOfUnits(totalQuantity % product.getNumberOfUnits())
                .numberOfCartons(totalQuantity / product.getNumberOfUnits())
                .customerName(customer.getName())
                .totalPrice(priceCalculation.calculatePrice(totalQuantity, product))
                .build();
    }

    private Customer getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(
                "Customer Not Found with id: " + customerId));
        return customer;
    }

    private Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(
                "Product Not Found with id: " + productId));
        return product;
    }


}
