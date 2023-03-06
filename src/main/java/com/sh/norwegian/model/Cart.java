package com.sh.norwegian.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cart", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "product_id"})})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPrice;
    private int numberOfCartons;
    private int numberOfUnits;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Customer customer;
}
