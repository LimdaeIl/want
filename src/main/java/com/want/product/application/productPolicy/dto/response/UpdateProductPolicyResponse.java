package com.want.product.application.productPolicy.dto.response;

import com.want.product.domain.entity.productPolicy.DiscountType;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateProductPolicyResponse(
    UUID id,
    String name,
    String description,
    DiscountType discountType,
    Integer value,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    boolean isActive,
    Integer minPurchaseAmount,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {
  public static UpdateProductPolicyResponse from(ProductPolicy productPolicy) {
    return UpdateProductPolicyResponse.builder()
        .id(productPolicy.getId())
        .name(productPolicy.getName())
        .description(productPolicy.getDescription())
        .discountType(productPolicy.getDiscountType())
        .value(productPolicy.getValue())
        .startedAt(productPolicy.getStartedAt())
        .endedAt(productPolicy.getEndedAt())
        .isActive(productPolicy.getIsActive())
        .minPurchaseAmount(productPolicy.getMinPurchaseAmount())
        .createdAt(productPolicy.getCreatedAt())
        .createdBy(productPolicy.getCreatedBy())
        .updatedAt(productPolicy.getUpdatedAt())
        .updatedBy(productPolicy.getUpdatedBy())
        .deletedAt(productPolicy.getDeletedAt())
        .deletedBy(productPolicy.getDeletedBy())
        .build();
  }
}
