package com.want.product.application.product.dto.request;

import com.want.product.domain.entity.product.SaleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.UUID;


public record CreateProductRequest(

    @NotNull(message = "카테고리 ID는 필수입니다.")
    UUID categoryId,

    @NotNull(message = "회사 ID는 필수입니다.")
    UUID companyId,

    @NotNull(message = "상품 정책 ID는 필수입니다.")
    UUID productPolicyId,

    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 100, message = "상품명은 100자 이내여야 합니다.")
    String name,

    @NotNull(message = "가격은 필수입니다.")
    @Positive(message = "가격은 양수여야 합니다.")
    Integer price,

    @NotNull(message = "재고 수량은 필수입니다.")
    @PositiveOrZero(message = "재고 수량은 0 이상이어야 합니다.")
    Integer quantity,

    String thumbnail,

    @NotNull(message = "판매 상태는 필수입니다.")
    SaleStatus saleStatus,

    @NotBlank(message = "상품 설명은 필수입니다.")
    @Size(max = 1000, message = "상품 설명은 1000자 이내여야 합니다.")
    String description

) {
}

