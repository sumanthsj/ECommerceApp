package com.techie.microservices.order.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, Integer quantity, BigDecimal price) {

}
