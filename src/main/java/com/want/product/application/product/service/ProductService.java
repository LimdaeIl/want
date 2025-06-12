package com.want.product.application.product.service;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.response.CreateProductResponse;
import com.want.product.application.product.dto.response.GetProductResponse;
import java.util.UUID;

public interface ProductService {
  CreateProductResponse createProduct(CreateProductRequest request);

  GetProductResponse getProduct(CustomUserDetails userDetails, UUID id);
}
