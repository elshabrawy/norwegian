package com.sh.norwegian.util;

import com.sh.norwegian.costants.AppProperties;
import com.sh.norwegian.model.Cart;
import com.sh.norwegian.model.Product;
import com.sh.norwegian.payload.product.ProductPricesResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceCalculationTest {
    @Autowired
    private PriceCalculation priceCalculation;
    @Autowired
    private AppProperties appProperties;

    private  Product mockProduct;
    private Cart mockCart;
    @BeforeEach
    void setUp() {
        mockProduct = Product.builder()
                .name("Product1")
                .price(new BigDecimal(175.00))
                .numberOfUnits(20)
                .build();
        mockCart = Cart.builder()
                .totalPrice(new BigDecimal(197.75))
                .numberOfCartons(1)
                .numberOfUnits(2)
                .build();
    }
    @Test
    void getProductPricesResponse() {
        //when
        List<ProductPricesResponse> responses = priceCalculation.getProductPricesResponse(mockProduct);
        //then
        assertEquals(responses.size(), appProperties.getLIST_COUNT());

        for (int i = 0; i < responses.size(); i++) {
            ProductPricesResponse response = responses.get(i);
            assertEquals(response.getName(), mockProduct.getName());
            assertEquals(response.getUnitsCount(), i+1);
            assertEquals(response.getUnitsPrice(), priceCalculation.calculatePrice(i+1, mockProduct));
        }
    }

    @Test
    void testGetTotalQuantity() {

        // Test case 1: cart is empty
        int result1 = priceCalculation.getTotalQuantity(10, 20, Optional.empty());
        assertEquals(10, result1);

        // Test case 2: cart is present
        int result2 = priceCalculation.getTotalQuantity(10, 20, Optional.of(mockCart));
        assertEquals(32, result2);
    }


    @Test
    void testCalculatePrice_validInput() {
        // given
        int countOfItems = 5;

        // when
        BigDecimal totalPrice = priceCalculation.calculatePrice(countOfItems, mockProduct);

        // then
        Assertions.assertEquals(new BigDecimal("56.875"), totalPrice);
    }

    @Test
    void testCalculatePrice_negativeItemCount() {
        // given
        int countOfItems = -1;

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            priceCalculation.calculatePrice(countOfItems, mockProduct);
        });
    }

    @Test
    void testCalculatePrice_zeroItemCount() {
        // given
        int countOfItems = 0;

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            priceCalculation.calculatePrice(countOfItems, mockProduct);
        });
    }

    @Test
    void testCalculatePrice_nullProduct() {
        // given
        int countOfItems = 10;

        // then
        Assertions.assertThrows(NullPointerException.class, () -> {
            priceCalculation.calculatePrice(countOfItems, null);
        });
    }


    @Test
    void testCalculatePrice_discountApplied() {
        // given
        int countOfItems = 100;

        // when
        BigDecimal totalPrice = priceCalculation.calculatePrice(countOfItems, mockProduct);

        // then
        Assertions.assertEquals(787.5, totalPrice.doubleValue());
    }
}




