package com.want.product.application.productPolicy.service;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.product.application.productPolicy.dto.request.CreateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.response.CreateProductPolicyResponse;

public interface ProductPolicyService {
  CreateProductPolicyResponse createProductPolicy(CustomUserDetails userDetails, CreateProductPolicyRequest request);
}
