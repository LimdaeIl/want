package com.want.product.application.product.dto.request;

import com.want.product.domain.entity.product.SaleStatus;

public record UpdateProductRequest(
    String name,
    Integer price,
    Integer quantity,
    String thumbnail,
    String description,
    SaleStatus saleStatus
) {
}
