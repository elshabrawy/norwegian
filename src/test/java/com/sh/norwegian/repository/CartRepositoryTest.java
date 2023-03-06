package com.sh.norwegian.repository;

import com.sh.norwegian.model.Cart;
import com.sh.norwegian.model.Customer;
import com.sh.norwegian.model.Product;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;
    private Product product;
    private Cart cart;
    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .email("test@gmail.com")
                .name("testCustomer")
                .build();
        customer = customerRepository.save(customer);
        product = Product.builder()
                .price(new BigDecimal(175))
                .numberOfUnits(20)
                .name("testProduct")
                .build();
        product = productRepository.save(product);
        cart = Cart.builder()
                .customer(customer)
                .totalPrice(new BigDecimal(197.75))
                .product(product)
                .numberOfCartons(1)
                .numberOfUnits(2)
                .build();
        cart = cartRepository.save(cart);

    }
    @Test
    public void testFindByCustomerIdAndProductId() {
        Optional<Cart> result = cartRepository.findByCustomerIdAndProductId(customer.getId(),product.getId());
        assertEquals(cart.getId(), result.get().getId());
    }

    @Test
    public void testFindByInvalidCustomerIdAndProductId_shouldReturnEmpty() {
        Optional<Cart> result = cartRepository.findByCustomerIdAndProductId(-1L,product.getId());
        assertThat(result).isEmpty();

    }
    @AfterEach
    void tearDown() {
        cartRepository.delete(cart);
        cart=null;
        productRepository.delete(product);
        product=null;
        customerRepository.delete(customer);
        customer=null;
    }

}