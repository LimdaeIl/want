package com.want.product.presentation;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.product.application.category.dto.request.CreateCategoriesRequest;
import com.want.product.application.category.dto.request.CreateCategoryRequest;
import com.want.product.application.category.dto.request.UpdateCategoryRequest;
import com.want.product.application.category.dto.response.CreateCategoriesResponse;
import com.want.product.application.category.dto.response.CreateCategoryResponse;
import com.want.product.application.category.dto.response.DeleteCategoryResponse;
import com.want.product.application.category.dto.response.GetCategoryResponse;
import com.want.product.application.category.service.CategoryService;
import com.want.product.domain.entity.category.CategorySuccessCode;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products/categories")
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  @PostMapping
  public ResponseEntity<ApiResponse<CreateCategoryResponse>> createCategory(
      @RequestBody @Valid CreateCategoryRequest request
  ) {
    CreateCategoryResponse response = categoryService.createCategory(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
                CategorySuccessCode.CATEGORY_CREATED.getCode(),
                CategorySuccessCode.CATEGORY_CREATED.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  @PostMapping("/tree")
  public ResponseEntity<ApiResponse<List<CreateCategoriesResponse>>> createCategories(
      @RequestBody @Valid List<CreateCategoriesRequest> request
  ) {
    List<CreateCategoriesResponse> response = categoryService.createCategories(request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                CategorySuccessCode.CATEGORY_CREATED.getCode(),
                CategorySuccessCode.CATEGORY_CREATED.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @GetMapping("{name}")
  public ResponseEntity<ApiResponse<GetCategoryResponse>> getCategory(
      @PathVariable String name
  ) {
    GetCategoryResponse response = categoryService.getCategory(name);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                CategorySuccessCode.CATEGORY_FETCHED.getCode(),
                CategorySuccessCode.CATEGORY_FETCHED.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  @PatchMapping("{id}")
  public ResponseEntity<ApiResponse<GetCategoryResponse>> updateCategory(
      @RequestBody @Valid UpdateCategoryRequest request,
      @PathVariable UUID id
  ) {
    GetCategoryResponse response = categoryService.updateCategory(id, request);

    return ResponseEntity.ok(
        new ApiResponse<>(
            CategorySuccessCode.CATEGORY_UPDATED.getCode(),
            CategorySuccessCode.CATEGORY_UPDATED.getMessage(),
            response
        )
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<List<DeleteCategoryResponse>>> deleteCategory(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable UUID id
  ) {
    List<DeleteCategoryResponse> response = categoryService.deleteCategory(userDetails, id);

    return ResponseEntity.ok(
        new ApiResponse<>(
            CategorySuccessCode.CATEGORY_DELETED.getCode(),
            CategorySuccessCode.CATEGORY_DELETED.getMessage(),
            response
        )
    );
  }

}
