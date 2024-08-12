package com.example.order.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id,
                           String orderNumber,
                           String skuCode,
                           BigDecimal price,
                           Integer quantity,
                           UserDetail userDetail) {
}
