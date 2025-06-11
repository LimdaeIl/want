package com.want.product.application.productPolicy.dto.response;

import com.want.product.domain.entity.productPolicy.ProductPolicy;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeleteProductPolicyResponse(
    UUID id,
    String name,
    LocalDateTime deletedAt,
    Long deletedBy

) {
  public static DeleteProductPolicyResponse from(ProductPolicy productPolicy) {
    return DeleteProductPolicyResponse.builder()
        .id(productPolicy.getId())
        .name(productPolicy.getName())
        .deletedAt(productPolicy.getDeletedAt())
        .deletedBy(productPolicy.getDeletedBy())
        .build();
  }
}
