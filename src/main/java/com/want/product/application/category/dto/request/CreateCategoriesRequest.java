package com.want.product.application.category.dto.request;

import java.util.List;

public record CreateCategoriesRequest(
    String name,
    List<CreateCategoriesRequest> children
) {
}
