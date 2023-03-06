package com.sh.norwegian.service;

import com.sh.norwegian.costants.AppProperties;
import com.sh.norwegian.exception.CustomerNotFoundException;
import com.sh.norwegian.exception.ProductNotFoundException;
import com.sh.norwegian.model.Customer;
import com.sh.norwegian.model.Product;
import com.sh.norwegian.payload.order.OrderRequest;
import com.sh.norwegian.payload.order.OrderResponse;
import com.sh.norwegian.payload.product.ProductPricesResponse;
import com.sh.norwegian.repository.CustomerRepository;
import com.sh.norwegian.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest


public class ProductServiceTest {


    @Autowired
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;

    private Long validProductId=1l;
    private Long validCustomerId=1l;
    @Autowired
    private AppProperties appProperties;

    @Test
    public void testGetListOfProductPrices_withValidProductId() {
        Product product = getProduct();
        List<ProductPricesResponse> responses = productService.getListOfProductPrices(product.getId());
        assertEquals(responses.size(), appProperties.getLIST_COUNT());

        for (int i = 0; i < responses.size(); i++) {
            ProductPricesResponse response = responses.get(i);
            assertEquals(response.getUnitsCount(), i+1);

        }
    }

    @Test
    public void testGetOrderDetails_shouldThrowProductNotFoundException() {
        //given
        Product product = getProduct();
        OrderRequest orderRequest=getOrderRequest();
        Long invalidProductId = product.getId() + 3;
        //when
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> {
            productService.getOrderDetails(invalidProductId,orderRequest);
        });
        //then
        assertEquals(productNotFoundException.getMessage(), ("Product Not Found with id: " + invalidProductId));

    }

    @Test
    public void testGetOrderDetails_shouldThrowCustomerNotFoundException() {
        //given
        Customer customer = getCustomer();
        OrderRequest orderRequest=getOrderRequest();
        Long invalidCustomerId = customer.getId() + 3;
        OrderRequest invalidOrderRequest=getOrderRequest();
        invalidOrderRequest.setCustomerId(invalidCustomerId);

        //when
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        //then
        CustomerNotFoundException customerNotFoundException = assertThrows(CustomerNotFoundException.class, () -> {
            productService.getOrderDetails(validProductId,invalidOrderRequest);
        });
        assertEquals(customerNotFoundException.getMessage(), ("Customer Not Found with id: " + invalidCustomerId));

    }

    @Test
    public void testGetOrderDetails_withValidProductId_validCustomerId() {
        //given
        Product product = getProduct();
        Customer customer = getCustomer();
        OrderRequest orderRequest=getOrderRequest();
        //when
        OrderResponse orderDetails = productService.getOrderDetails(validProductId,orderRequest);
        //then
        assertEquals(orderDetails.getTotalPrice(),new BigDecimal(463.75));
        assertEquals(orderDetails.getNumberOfCartons(),2);
        assertEquals(orderDetails.getNumberOfUnits(),10);

    }


    private Product getProduct() {
        return Product.builder()
                .id(validProductId)
                .name("Product1")
                .price(new BigDecimal(175))
                .numberOfUnits(20)
                .build();
    }

    private Customer getCustomer() {
        return Customer.builder()
                .email("mohamed@gmail.com")
                .name("mohamed")
                .id(validCustomerId)
                .build();
    }

    private OrderRequest getOrderRequest() {
        return OrderRequest.builder()
                .quantity(50)
                .customerId(validCustomerId)
                .build();
    }


}