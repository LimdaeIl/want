package com.want.product.application.category.dto.request;

import java.util.UUID;

public record CreateCategoryRequest(

    String name,
    UUID parentId
) {
}