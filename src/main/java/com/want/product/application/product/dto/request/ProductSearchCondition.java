package com.want.product.application.product.dto.request;

import com.want.product.domain.entity.product.SaleStatus;
import com.want.product.domain.entity.productPolicy.DiscountType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;


public record ProductSearchCondition(
    UUID id,                        // 상품 ID
    String name,                   // 상품명 키워드 검색
    Integer minPrice,              // 최소 가격
    Integer maxPrice,              // 최대 가격
    Integer minQuantity,           // 최소 수량
    Integer maxQuantity,           // 최대 수량
    SaleStatus saleStatus,         // 판매 상태
    String thumbnail,              // 썸네일 URL (정확히 일치 검색)
    String description,            // 설명 키워드 검색

    UUID categoryId,               // 카테고리 ID
    UUID companyId,                // 회사 ID
    UUID productPolicyId,          // 상품 정책 ID

    ZonedDateTime createdFrom,     // 생성일 범위 시작
    ZonedDateTime createdTo,       // 생성일 범위 끝
    ZonedDateTime updatedFrom,     // 수정일 범위 시작
    ZonedDateTime updatedTo,       // 수정일 범위 끝
    ZonedDateTime deletedFrom,     // 삭제일 범위 시작
    ZonedDateTime deletedTo,       // 삭제일 범위 끝
    Boolean includeDeleted,        // 삭제된 상품 포함 여부 (soft delete)

    // 상품정책 조건
    DiscountType discountType,
    Boolean isPolicyActive,
    Integer minPolicyValue,
    Integer maxPolicyValue,
    Integer minPurchaseAmount,
    LocalDateTime policyStartedFrom,
    LocalDateTime policyStartedTo,
    LocalDateTime policyEndedFrom,
    LocalDateTime policyEndedTo

) {
}