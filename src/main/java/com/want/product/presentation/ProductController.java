package com.want.product.presentation;

import com.want.common.response.ApiResponse;
import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.response.CreateProductResponse;
import com.want.product.application.product.service.ProductService;
import com.want.product.domain.entity.product.ProductSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

  private final ProductService productService;

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
}
