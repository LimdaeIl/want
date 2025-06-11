package com.want.product.application.productPolicy.dto.response;

import com.want.company.domain.entity.Company;
import com.want.product.domain.entity.productPolicy.DiscountType;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateProductPolicyResponse(
    UUID id,
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
