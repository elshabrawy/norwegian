package com.sh.norwegian.util;

import com.sh.norwegian.costants.AppProperties;
import com.sh.norwegian.model.Cart;
import com.sh.norwegian.model.Product;
import com.sh.norwegian.payload.product.ProductPricesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceCalculation {
    private final AppProperties appProperties;

    public List<ProductPricesResponse> getProductPricesResponse(Product product) {
        List<ProductPricesResponse> result = new ArrayList<>();
        for (int rowNumber = 1; rowNumber <  appProperties.getLIST_COUNT() + 1; rowNumber++) {
            result.add(ProductPricesResponse.builder()
                    .name(product.getName())
                    .unitsCount(rowNumber)
                    .unitsPrice(calculatePrice(rowNumber, product))
                    .build());
        }
        return result;
    }


    public  int getTotalQuantity(int quantity, int  numberOfUnits, Optional<Cart> productHistory) {

        if (productHistory.isPresent()) {
            quantity += (productHistory.get().getNumberOfCartons() * numberOfUnits + productHistory.get().getNumberOfUnits());
        }
        return quantity;
    }

    public BigDecimal calculatePrice(int countOfItems, Product product) {
        int numberOfItems = countOfItems % product.getNumberOfUnits();
        int totalNumberOfCarton = countOfItems / product.getNumberOfUnits();
        BigDecimal itemPrice = calculateItemPrice(product);
        BigDecimal priceOfItems = calculatePriceOfItems(numberOfItems, itemPrice);
        priceOfItems = addAdditionalFees(priceOfItems);
        BigDecimal priceOfCartons = calculatePriceOfCartons(totalNumberOfCarton, product.getPrice());
        BigDecimal totalPrice = priceOfCartons.add(priceOfItems);

        if (totalNumberOfCarton >= appProperties.getCOUNT_TO_GET_DISCOUNT()) {
            totalPrice = applyDiscount(totalPrice);
        }

        return totalPrice;
    }


    private BigDecimal calculateItemPrice(Product product) {
        return product.getPrice().divide(new BigDecimal(product.getNumberOfUnits()));
    }

    private BigDecimal calculatePriceOfItems(int numberOfItems, BigDecimal itemPrice) {
        return new BigDecimal(numberOfItems).multiply(itemPrice);
    }

    private BigDecimal addAdditionalFees(BigDecimal priceOfItems) {
        return priceOfItems.add((priceOfItems.multiply(appProperties.getADDITION_FEES())).divide(new BigDecimal(100)));
    }

    private BigDecimal calculatePriceOfCartons(int totalNumberOfCarton, BigDecimal cartonPrice) {
        return cartonPrice.multiply(new BigDecimal(totalNumberOfCarton));
    }

    private BigDecimal applyDiscount(BigDecimal totalPrice) {
        BigDecimal discountPercentage = new BigDecimal(appProperties.getDISCOUNT_PERCENTAGE());
        return totalPrice.multiply(new BigDecimal((100 - discountPercentage.doubleValue()) / 100));
    }


}
