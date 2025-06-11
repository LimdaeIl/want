package com.want.product.application.productPolicy.service;

import com.want.common.exception.CustomException;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.company.domain.entity.Company;
import com.want.company.domain.repository.CompanyRepository;
import com.want.product.application.productPolicy.dto.request.CreateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.response.CreateProductPolicyResponse;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import com.want.product.domain.entity.productPolicy.ProductPolicyErrorCode;
import com.want.product.domain.repository.ProductPolicyRepository;
import com.want.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductPolicyServiceImpl implements ProductPolicyService {

  private final ProductPolicyRepository productPolicyRepository;
  private final CompanyRepository companyRepository;
  private final ProductRepository productRepository;

  private void existsFindProductPolicyByName(String name) {
    if (productPolicyRepository.existsFindProductPolicyByName(name)) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_NAME_DUPLICATE);
    }

  }

  @Transactional
  @Override
  public CreateProductPolicyResponse createProductPolicy(CustomUserDetails userDetails,
                                                         CreateProductPolicyRequest request) {
    existsFindProductPolicyByName(request.name());

    Company company = companyRepository.findCompanyById(request.companyId())
        .orElseThrow(() -> new CustomException(ProductPolicyErrorCode.POLICY_APPLY_TARGET_NOT_FOUND));

    ProductPolicy productPolicy = ProductPolicy.builder()
        .company(company)
        .name(request.name())
        .description(request.description())
        .discountType(request.discountType())
        .value(request.value())
        .startedAt(request.startedAt())
        .endedAt(request.endedAt())
        .isActive(request.isActive())
        .minPurchaseAmount(request.minPurchaseAmount())
        .build();

    company.addProductPolicy(productPolicy);

    ProductPolicy saveProductPolicy = productPolicyRepository.save(productPolicy);

    return CreateProductPolicyResponse.from(saveProductPolicy);
  }
}
