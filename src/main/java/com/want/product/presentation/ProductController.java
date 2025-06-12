package com.want.product.presentation;

import com.want.common.config.PagedResponse;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.request.ProductSearchCondition;
import com.want.product.application.product.dto.request.UpdateProductRequest;
import com.want.product.application.product.dto.response.CreateProductResponse;
import com.want.product.application.product.dto.response.GetProductResponse;
import com.want.product.application.product.dto.response.GetProductsResponse;
import com.want.product.application.product.dto.response.UpdateProductResponse;
import com.want.product.application.product.service.ProductService;
import com.want.product.domain.entity.product.ProductSuccessCode;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

  private final ProductService productService;

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @PostMapping
  public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(
      @RequestBody @Valid CreateProductRequest request
  ) {
    CreateProductResponse response = productService.createProduct(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
                ProductSuccessCode.PRODUCT_CREATED.getCode(),
                ProductSuccessCode.PRODUCT_CREATED.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GetProductResponse>> getProduct(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable UUID id
  ) {
    GetProductResponse response = productService.getProduct(userDetails, id);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                ProductSuccessCode.PRODUCT_FETCHED.getCode(),
                ProductSuccessCode.PRODUCT_FETCHED.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @GetMapping
  public ResponseEntity<ApiResponse<PagedResponse<GetProductsResponse>>> getProducts(
      @ParameterObject ProductSearchCondition condition,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Direction.DESC),
          @SortDefault(sort = "id", direction = Direction.DESC)
      })
      Pageable pageable
  ) {
    PagedResponse<GetProductsResponse> response = productService.getProducts(condition, pageable);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                ProductSuccessCode.PRODUCT_FETCHED.getCode(),
                ProductSuccessCode.PRODUCT_FETCHED.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<UpdateProductResponse>> updateProduct(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody UpdateProductRequest request,
      @PathVariable UUID id
  ) {
    UpdateProductResponse response = productService.updateProduct(userDetails, request, id);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                ProductSuccessCode.PRODUCT_UPDATED.getCode(),
                ProductSuccessCode.PRODUCT_UPDATED.getMessage(),
                response
            )
        );
  }
}
