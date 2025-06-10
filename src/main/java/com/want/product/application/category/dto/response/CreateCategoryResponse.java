package com.want.product.application.category.dto.response;

import com.want.product.domain.entity.category.Category;
import java.util.UUID;

public record CreateCategoryResponse(
    UUID id,
    String name,
    UUID parentId
) {
  public static CreateCategoryResponse from(Category category) {
    return new CreateCategoryResponse(
        category.getId(),
        category.getName(),
        category.getParentId() != null ? category.getParentId().getId() : null
    );
  }
}