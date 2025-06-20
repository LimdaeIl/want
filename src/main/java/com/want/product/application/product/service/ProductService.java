package com.want.product.application.product.service;

import com.want.common.config.PagedResponse;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.request.ProductSearchCondition;
import com.want.product.application.product.dto.request.UpdateProductRequest;
import com.want.product.application.product.dto.response.CreateProductResponse;
import com.want.product.application.product.dto.response.DeleteProductResponse;
import com.want.product.application.product.dto.response.GetProductResponse;
import com.want.product.application.product.dto.response.GetProductsResponse;
import com.want.product.application.product.dto.response.UpdateProductResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  CreateProductResponse createProduct(CreateProductRequest request);

  GetProductResponse getProduct(CustomUserDetails userDetails, UUID id);

  PagedResponse<GetProductsResponse> getProducts(ProductSearchCondition condition, Pageable pageable);

  UpdateProductResponse updateProduct(CustomUserDetails userDetails, UpdateProductRequest request, UUID id);

  DeleteProductResponse deleteProduct(CustomUserDetails userDetails, UUID id);
}
