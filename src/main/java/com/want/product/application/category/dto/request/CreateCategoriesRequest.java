package com.want.product.application.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CreateCategoriesRequest(
    @NotBlank(message = "카테고리 이름: 카테고리 이름은 필수 입니다.")
    String name,
    List<CreateCategoriesRequest> children
) {
}
