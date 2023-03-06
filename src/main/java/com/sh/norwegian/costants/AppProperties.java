package com.sh.norwegian.costants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties
public class AppProperties {

    @Value("${app.COUNT_TO_GET_DISCOUNT}")
    private double COUNT_TO_GET_DISCOUNT;
    @Value("${app.DISCOUNT_PERCENTAGE}")
    private double DISCOUNT_PERCENTAGE;
    @Value("${app.ADDITION_FEES}")
    private BigDecimal ADDITION_FEES;
    @Value("${app.LIST_COUNT}")
    private int LIST_COUNT;



}