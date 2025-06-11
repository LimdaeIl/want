package com.want.product.application.productPolicy.dto.response;

import com.want.company.domain.entity.Company;
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
public record GetProductPolicyResponse(
    UUID id,
    String name,
    String description,
    DiscountType discountType,
    Integer value,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    boolean isActive,
    Integer minPurchaseAmount,
    AssignCompany assignCompany,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy,
    List<ApplyProducts> applyProducts
) {
  public static GetProductPolicyResponse from(ProductPolicy byId) {
    return GetProductPolicyResponse.builder()
        .id(byId.getId())
        .name(byId.getName())
        .description(byId.getDescription())
        .discountType(byId.getDiscountType())
        .value(byId.getValue())
        .startedAt(byId.getStartedAt())
        .endedAt(byId.getEndedAt())
        .isActive(byId.getIsActive())
        .minPurchaseAmount(byId.getMinPurchaseAmount())
        .createdAt(byId.getCreatedAt())
        .createdBy(byId.getCreatedBy())
        .updatedAt(byId.getUpdatedAt())
        .updatedBy(byId.getUpdatedBy())
        .deletedAt(byId.getDeletedAt())
        .deletedBy(byId.getDeletedBy())
        .assignCompany(AssignCompany.from(byId.getCompany()))
        .applyProducts(ApplyProducts.from(byId.getProducts()))
        .build();
  }

  @Builder(access = AccessLevel.PRIVATE)
  private record AssignCompany(
      UUID id,
      String name
  ) {

    private static AssignCompany from(Company company) {
      if (company == null) {
        return null;
      }

      return AssignCompany.builder()
          .id(company.getId())
          .name(company.getName())
          .build();
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  private record ApplyProducts(
      UUID id,
      String name,
      Integer price,
      Integer quantity,
      String thumbnail,
      SaleStatus saleStatus,
      String description,
      UUID companyId,
      UUID categoryId
  ) {
    private static List<ApplyProducts> from(List<Product> products) {
      return products.stream()
          .map(product -> ApplyProducts.builder()
              .id(product.getId())
              .categoryId(product.getCategory().getId())
              .companyId(product.getCompany().getId())
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
}
