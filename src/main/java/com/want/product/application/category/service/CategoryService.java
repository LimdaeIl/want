package com.want.product.application.category.service;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.product.application.category.dto.request.CreateCategoriesRequest;
import com.want.product.application.category.dto.request.CreateCategoryRequest;
import com.want.product.application.category.dto.request.GetCategoryRequest;
import com.want.product.application.category.dto.request.UpdateCategoryRequest;
import com.want.product.application.category.dto.response.CreateCategoriesResponse;
import com.want.product.application.category.dto.response.CreateCategoryResponse;
import com.want.product.application.category.dto.response.DeleteCategoryResponse;
import com.want.product.application.category.dto.response.GetCategoryResponse;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
  CreateCategoryResponse createCategory(CreateCategoryRequest request);

  List<CreateCategoriesResponse> createCategories(List<CreateCategoriesRequest> request);

  GetCategoryResponse getCategory(GetCategoryRequest request);

  GetCategoryResponse updateCategory(UUID id, UpdateCategoryRequest request);

  List<DeleteCategoryResponse> deleteCategory(CustomUserDetails userDetails, UUID id);
}
