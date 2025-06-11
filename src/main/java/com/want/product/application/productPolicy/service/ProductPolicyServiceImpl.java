package com.want.product.application.productPolicy.service;

import com.want.common.exception.CustomException;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.company.domain.entity.Company;
import com.want.company.domain.repository.CompanyRepository;
import com.want.product.application.productPolicy.dto.request.CreateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.request.UpdateProductPolicyRequest;
import com.want.product.application.productPolicy.dto.response.CreateProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.DeleteProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.GetProductPolicyResponse;
import com.want.product.application.productPolicy.dto.response.UpdateProductPolicyResponse;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import com.want.product.domain.entity.productPolicy.ProductPolicyErrorCode;
import com.want.product.domain.repository.ProductPolicyRepository;
import com.want.product.domain.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductPolicyServiceImpl implements ProductPolicyService {

  private final ProductPolicyRepository productPolicyRepository;
  private final CompanyRepository companyRepository;
  private final ProductRepository productRepository;

  private void existsFindProductPolicyByName(String newName) {
    if (productPolicyRepository.existsFindProductPolicyByName(newName)) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_NAME_DUPLICATE);
    }
  }

  private ProductPolicy findById(UUID id) {
    return productPolicyRepository.findById(id)
        .orElseThrow(() -> new CustomException(ProductPolicyErrorCode.POLICY_ID_NOT_FOUND));
  }


  @Transactional
  @Override
  public CreateProductPolicyResponse createProductPolicy(CustomUserDetails userDetails,
                                                         CreateProductPolicyRequest request) {
    existsFindProductPolicyByName(request.name());

    Company company = companyRepository.findById(request.companyId())
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

  @Transactional(readOnly = true)
  @Override
  public GetProductPolicyResponse getProductPolicy(UUID id) {
    ProductPolicy byId = findById(id);

    return GetProductPolicyResponse.from(byId);
  }

  @Transactional
  @Override
  public UpdateProductPolicyResponse updateProductPolicy(
      CustomUserDetails userDetails,
      UpdateProductPolicyRequest request,
      UUID id) {
    existsFindProductPolicyByName(request.newName());
    ProductPolicy byId = findById(id);

    byId.updateName(request.newName());
    byId.updateDescription(request.newDescription());
    byId.updateDiscountType(request.newDiscountType());
    byId.updateValue(request.newValue());
    byId.updateStartedAt(request.newStartedAt());
    byId.updateEndedAt(request.newEndedAt());
    byId.updateIsActive(request.newIsActive());
    byId.updateMinPurchaseAmount(request.newMinPurchaseAmount());

    ProductPolicy saveProductPolicy = productPolicyRepository.save(byId);

    return UpdateProductPolicyResponse.from(saveProductPolicy);
  }

  @Transactional
  @Override
  public DeleteProductPolicyResponse deleteProductPolicy(CustomUserDetails userDetails, UUID id) {
    ProductPolicy byId = findById(id);

    byId.markDeleted(userDetails.id());

    return DeleteProductPolicyResponse.from(byId);
  }
}
