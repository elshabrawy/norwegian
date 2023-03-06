package com.sh.norwegian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.norwegian.payload.order.OrderRequest;
import com.sh.norwegian.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;
    private Long validProductId = 1l;
    private Long invalidProductId = null;

    @Test
    public void getProductPrices_returnsListOfPrices() throws Exception {
        String url="/api/product/"+validProductId+"/prices";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();

    }

    @Test
    public void getProductPrices_returns_NotFoundAsInputIsRequired() throws Exception {

        String url = "/api/product/" + invalidProductId + "prices";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();

    }

    @Test
    public void testGetOrderDetails() throws Exception {

        // Create a sample order request
        String url="/api/product/"+validProductId+"/order-details";
        OrderRequest orderRequest = OrderRequest.builder()
                .customerId(1l)
                .quantity(30)
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void testGetOrderDetails_NotFoundAsInputIsRequired() throws Exception {
        // Create a sample order request

        String url="/api/product/"+invalidProductId+"/order-details";
        OrderRequest orderRequest = OrderRequest.builder()
                .customerId(1l)
                .quantity(30)
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEmpty();
//        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

}