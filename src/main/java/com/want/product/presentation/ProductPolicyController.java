package com.want.product.presentation;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.product.application.productPolicy.dto.request.CreateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.response.CreateProductPolicyResponse;
import com.want.product.application.productPolicy.service.ProductPolicyService;
import com.want.product.domain.entity.productPolicy.ProductPolicySuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products/policies")
@RestController
public class ProductPolicyController {

  private final ProductPolicyService productPolicyService;

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @PostMapping
  public ResponseEntity<ApiResponse<CreateProductPolicyResponse>> createProductPolicy(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid CreateProductPolicyRequest request) {
    CreateProductPolicyResponse response = productPolicyService.createProductPolicy(userDetails, request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
                ProductPolicySuccessCode.POLICY_CREATED.getCode(),
                ProductPolicySuccessCode.POLICY_CREATED.getMessage(),
                response
            )
        );
  }
}
