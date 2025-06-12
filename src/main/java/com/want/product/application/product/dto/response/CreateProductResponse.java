package com.want.product.application.product.dto.response;

import com.want.company.domain.entity.Company;
import com.want.product.domain.entity.category.Category;
import com.want.product.domain.entity.product.Product;
import com.want.product.domain.entity.product.SaleStatus;
import com.want.product.domain.entity.productPolicy.DiscountType;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateProductResponse(
    UUID id,
    String name,
    Integer price,
    Integer quantity,
    String thumbnail,
    SaleStatus saleStatus,
    String description,
    BelongToCategory belongToCategory,
    AssignCompany assignCompany,
    ApplyProductPolicy applyProductPolicy
) {
  public static CreateProductResponse from(Product product) {
    return CreateProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .quantity(product.getQuantity())
        .thumbnail(product.getThumbnail())
        .saleStatus(product.getSaleStatus())
        .description(product.getDescription())
        .belongToCategory(BelongToCategory.from(product.getCategory()))
        .assignCompany(AssignCompany.from(product.getCompany()))
        .applyProductPolicy(ApplyProductPolicy.from(product.getProductPolicy()))
        .build();
  }


  @Builder(access = AccessLevel.PRIVATE)
  private record BelongToCategory(
      UUID id,
      String name
  ) {
    public static BelongToCategory from(Category category) {
      return BelongToCategory.builder()
          .id(category.getId())
          .name(category.getName())
          .build();
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  private record AssignCompany(
      UUID id,
      String name
  ) {
    public static AssignCompany from(Company company) {
      return AssignCompany.builder()
          .id(company.getId())
          .name(company.getName())
          .build();
    }

  }

  @Builder(access = AccessLevel.PRIVATE)
  private record ApplyProductPolicy(
      UUID id,
      String name,
      String description,
      DiscountType discountType,
      Integer value,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      boolean isActive,
      Integer minPurchaseAmount
  ) {
    public static ApplyProductPolicy from(ProductPolicy productPolicy) {
      return ApplyProductPolicy.builder()
          .id(productPolicy.getId())
          .name(productPolicy.getName())
          .description(productPolicy.getDescription())
          .discountType(productPolicy.getDiscountType())
          .value(productPolicy.getValue())
          .startedAt(productPolicy.getStartedAt())
          .endedAt(productPolicy.getEndedAt())
          .isActive(productPolicy.isCurrentlyActive())
          .minPurchaseAmount(productPolicy.getMinPurchaseAmount())
          .build();
    }
  }


}
