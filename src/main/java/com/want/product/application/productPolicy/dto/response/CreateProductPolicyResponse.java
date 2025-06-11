package com.want.product.application.productPolicy.dto.response;

import com.want.company.domain.entity.Company;
import com.want.product.domain.entity.category.Category;
import com.want.product.domain.entity.product.Product;
import com.want.product.domain.entity.product.SaleStatus;
import com.want.product.domain.entity.productPolicy.DiscountType;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateProductPolicyResponse(
    UUID id,
    List<ApplyProducts> applyProducts,
    AssignCompany assignCompany,
    String name,
    String description,
    DiscountType discountType,
    Integer value,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    boolean isActive,
    Integer minPurchaseAmount
) {
  public static CreateProductPolicyResponse from(ProductPolicy productPolicy) {
     return CreateProductPolicyResponse.builder()
        .id(productPolicy.getId())
        .applyProducts(ApplyProducts.from(productPolicy.getProducts()))
        .assignCompany(AssignCompany.from(productPolicy.getCompany()))
        .name(productPolicy.getName())
        .description(productPolicy.getDescription())
        .discountType(productPolicy.getDiscountType())
        .value(productPolicy.getValue())
        .startedAt(productPolicy.getStartedAt())
        .endedAt(productPolicy.getEndedAt())
        .isActive(productPolicy.getIsActive())
        .minPurchaseAmount(productPolicy.getMinPurchaseAmount())
        .build();
  }

  @Builder(access = AccessLevel.PRIVATE)
  record ApplyProducts(
      UUID id,
      Category category,
      String name,
      Integer price,
      Integer quantity,
      String thumbnail,
      SaleStatus saleStatus,
      String description
  ) {

    public static List<ApplyProducts> from(List<Product> products) {
      return products.stream()
          .map(product -> ApplyProducts.builder()
              .id(product.getId())
              .category(product.getCategory())
              .name(product.getName())
              .price(product.getPrice())
              .quantity(product.getQuantity())
              .thumbnail(product.getThumbnail())
              .saleStatus(product.getSaleStatus())
              .description(product.getDescription())
              .build())
          .toList();
    }

  }

  @Builder(access = AccessLevel.PRIVATE)
  record AssignCompany(
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


}
