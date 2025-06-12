package com.want.product.application.product.dto.response;

import com.want.product.domain.entity.product.Product;
import com.want.product.domain.entity.product.SaleStatus;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record UpdateProductResponse(
    UUID id,
    String name,
    Integer price,
    Integer quantity,
    SaleStatus saleStatus
) {
  public static UpdateProductResponse from(Product product) {
    return UpdateProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .quantity(product.getQuantity())
        .saleStatus(product.getSaleStatus())
        .build();
  }
}