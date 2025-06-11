package com.want.product.application.productPolicy.dto.request;

import com.want.product.domain.entity.productPolicy.DiscountType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record UpdateProductPolicyRequest(
    @NotBlank(message = "정책 이름은 필수입니다.")
    @Size(max = 100, message = "정책 이름은 100자 이내여야 합니다.")
    String newName,

    @Size(max = 255, message = "설명은 255자 이내여야 합니다.")
    String newDescription,

    @NotNull(message = "할인 유형은 필수입니다.")
    DiscountType newDiscountType,

    @NotNull(message = "할인 값은 필수입니다.")
    @Min(value = 0, message = "할인 값은 0 이상이어야 합니다.")
    @Max(value = 100, message = "할인 값은 100 이하이어야 합니다.")
    Integer newValue,

    @NotNull(message = "시작일시는 필수입니다.")
    @FutureOrPresent(message = "시작일시는 현재 또는 미래여야 합니다.")
    LocalDateTime newStartedAt,

    @NotNull(message = "종료일시는 필수입니다.")
    @Future(message = "종료일시는 미래여야 합니다.")
    LocalDateTime newEndedAt,

    boolean newIsActive,

    @Min(value = 0, message = "최소 구매 금액은 0 이상이어야 합니다.")
    Integer newMinPurchaseAmount) {
}
