package com.want.product.application.category.dto.request;

import java.util.UUID;

public record UpdateCategoryRequest(
    String newName,
    UUID newParentId
) {
}
