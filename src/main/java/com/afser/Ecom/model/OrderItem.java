package com.afser.Ecom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)

private Order order;
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
private Product product;
private Integer quantity;
private BigDecimal price;


}