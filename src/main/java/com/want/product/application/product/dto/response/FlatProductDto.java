package com.want.product.application.product.dto.response;

import com.want.product.domain.entity.product.SaleStatus;
import com.want.product.domain.entity.productPolicy.DiscountType;
import java.time.LocalDateTime;
import java.util.UUID;

public record FlatProductDto(
    UUID id,
    String name,
    Integer price,
    Integer quantity,
    String thumbnail,
    SaleStatus saleStatus,
    String description,

    UUID categoryId,
    String categoryName,
    UUID companyId,
    String companyName,
    UUID productPolicyId,
    String policyName,
    String policyDescription,
    DiscountType discountType,
    Integer discountValue,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    Boolean isPolicyActive,
    Integer minPurchaseAmount,

    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {}