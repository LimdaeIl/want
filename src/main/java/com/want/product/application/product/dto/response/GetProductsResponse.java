package com.want.product.application.product.dto.response;

import com.want.product.domain.entity.product.SaleStatus;
import com.want.product.domain.entity.productPolicy.DiscountType;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetProductsResponse(
    UUID id,
    String name,
    Integer price,
    Integer quantity,
    String thumbnail,
    SaleStatus saleStatus,
    String description,

    BelongToCategory belongToCategory,
    AssignCompany assignCompany,
    ApplyProductPolicy applyProductPolicy,

    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {

  public record BelongToCategory(UUID id, String name) {
  }

  public record AssignCompany(UUID id, String name) {
  }

  public record ApplyProductPolicy(
      UUID id,
      String name,
      String description,
      DiscountType discountType,
      Integer value,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      Boolean isActive,
      Integer minPurchaseAmount
  ) {
  }

  public static GetProductsResponse from(FlatProductDto f) {
    return new GetProductsResponse(
        f.id(),
        f.name(),
        f.price(),
        f.quantity(),
        f.thumbnail(),
        f.saleStatus(),
        f.description(),

        new BelongToCategory(f.categoryId(), f.categoryName()),
        new AssignCompany(f.companyId(), f.companyName()),
        new ApplyProductPolicy(
            f.productPolicyId(), f.policyName(), f.policyDescription(),
            f.discountType(), f.discountValue(), f.startedAt(),
            f.endedAt(), f.isPolicyActive(), f.minPurchaseAmount()
        ),

        f.createdAt(), f.createdBy(),
        f.updatedAt(), f.updatedBy(),
        f.deletedAt(), f.deletedBy()
    );
  }
}