package com.afser.Ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
        private Long id;
        private Long productId;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subTotal;
}
