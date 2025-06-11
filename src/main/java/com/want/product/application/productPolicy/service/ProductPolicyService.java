package com.want.product.application.productPolicy.service;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.product.application.productPolicy.dto.request.CreateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.request.UpdateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.response.CreateProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.GetProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.UpdateProductPolicyResponse;
import jakarta.validation.Valid;
import java.util.UUID;

public interface ProductPolicyService {
  CreateProductPolicyResponse createProductPolicy(CustomUserDetails userDetails, CreateProductPolicyRequest request);

  GetProductPolicyResponse getProductPolicy(UUID id);

  UpdateProductPolicyResponse updateProductPolicy(CustomUserDetails userDetails, UpdateProductPolicyRequest request, UUID id);
}
