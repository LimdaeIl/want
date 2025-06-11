package com.want.product.presentation;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.product.application.productPolicy.dto.request.CreateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.request.UpdateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.response.CreateProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.GetProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.UpdateProductPolicyResponse;
import com.want.product.application.productPolicy.service.ProductPolicyService;
import com.want.product.domain.entity.productPolicy.ProductPolicySuccessCode;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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


  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GetProductPolicyResponse>> getProductPolicy(
      @PathVariable UUID id) {
    GetProductPolicyResponse response = productPolicyService.getProductPolicy(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                ProductPolicySuccessCode.POLICY_FETCHED.getCode(),
                ProductPolicySuccessCode.POLICY_FETCHED.getMessage(),
                response
            )
        );
  }


  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<UpdateProductPolicyResponse>> updateProductPolicy(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid UpdateProductPolicyRequest request,
      @PathVariable UUID id) {
    UpdateProductPolicyResponse response = productPolicyService.updateProductPolicy(userDetails, request, id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                ProductPolicySuccessCode.POLICY_UPDATED.getCode(),
                ProductPolicySuccessCode.POLICY_UPDATED.getMessage(),
                response
            )
        );
  }



}
