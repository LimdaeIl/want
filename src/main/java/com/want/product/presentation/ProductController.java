package com.want.product.presentation;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.response.CreateProductResponse;
import com.want.product.application.product.dto.response.GetProductResponse;
import com.want.product.application.product.service.ProductService;
import com.want.product.domain.entity.product.ProductSuccessCode;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
                ProductSuccessCode.PRODUCT_FETCHED.getCode(),
                ProductSuccessCode.PRODUCT_FETCHED.getMessage(),
                response
            )
        );
  }
}
