package com.want.product.application.product.service;

import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.response.CreateProductResponse;

public interface ProductService {
  CreateProductResponse createProduct(CreateProductRequest request);
}
