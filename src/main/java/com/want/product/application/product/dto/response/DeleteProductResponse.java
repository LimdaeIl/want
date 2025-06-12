package com.want.product.application.product.dto.response;

import com.want.product.domain.entity.product.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeleteProductResponse(
    UUID id,
    String name,
    LocalDateTime deletedAt,
    Long deletedBy
) {
  public static DeleteProductResponse from(Product product) {
    return DeleteProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .deletedAt(product.getDeletedAt())
        .deletedBy(product.getDeletedBy())
        .build();
  }
}
