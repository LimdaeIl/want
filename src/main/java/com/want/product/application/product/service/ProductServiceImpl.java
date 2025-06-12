package com.want.product.application.product.service;

import com.want.common.config.PagedResponse;
import com.want.common.exception.CustomException;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.company.domain.entity.Company;
import com.want.company.domain.repository.CompanyRepository;
import com.want.product.application.product.dto.request.CreateProductRequest;
import com.want.product.application.product.dto.request.ProductSearchCondition;
import com.want.product.application.product.dto.response.CreateProductResponse;
import com.want.product.application.product.dto.response.FlatProductDto;
import com.want.product.application.product.dto.response.GetProductResponse;
import com.want.product.application.product.dto.response.GetProductsResponse;
import com.want.product.domain.entity.category.Category;
import com.want.product.domain.entity.product.Product;
import com.want.product.domain.entity.product.ProductErrorCode;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import com.want.product.domain.repository.CategoryRepository;
import com.want.product.domain.repository.ProductPolicyRepository;
import com.want.product.domain.repository.ProductQuerydslRepository;
import com.want.product.domain.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductQuerydslRepository productQuerydslRepository;
  private final ProductPolicyRepository productPolicyRepository;
  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;

  private Category findCategoryById(UUID categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
  }

  private ProductPolicy findProductPolicyById(UUID policyId) {
    return productPolicyRepository.findById(policyId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_POLICY_NOT_FOUND));
  }

  private Company findCompanyById(UUID companyId) {
    return companyRepository.findById(companyId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_COMPANY_NOT_FOUND));
  }

  private void existsByName(String name) {
    if (productRepository.existsByName(name)) {
      throw new CustomException(ProductErrorCode.PRODUCT_NAME_DUPLICATE);
    }
  }

  private Product findById(UUID id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_ID_NOT_FOUND));
  }

  @Transactional
  @Override
  public CreateProductResponse createProduct(CreateProductRequest request) {
    existsByName(request.name());

    Company companyById = findCompanyById(request.companyId());
    Category categoryById = findCategoryById(request.categoryId());
    ProductPolicy productPolicyById = findProductPolicyById(request.productPolicyId());

    Product buildProduct = Product.builder()
        .category(categoryById)
        .company(companyById)
        .productPolicy(productPolicyById)
        .name(request.name())
        .price(request.price())
        .quantity(request.quantity())
        .thumbnail(request.thumbnail())
        .saleStatus(request.saleStatus())
        .description(request.description())
        .build();

    companyById.addProduct(buildProduct);
    categoryById.addProduct(buildProduct);
    productPolicyById.addProduct(buildProduct);

    Product saveProduct = productRepository.save(buildProduct);

    return CreateProductResponse.from(saveProduct);
  }


  @Transactional(readOnly = true)
  @Override
  public GetProductResponse getProduct(CustomUserDetails userDetails, UUID id) {
    Product byId = findById(id);

    return GetProductResponse.from(byId);
  }

  @Transactional(readOnly = true)
  @Override
  public PagedResponse<GetProductsResponse> getProducts(ProductSearchCondition condition, Pageable pageable) {
    Page<FlatProductDto> productsByCondition = productQuerydslRepository.findProductsByCondition(condition,
        pageable);

    List<GetProductsResponse> content = productsByCondition.getContent().stream()
        .map(GetProductsResponse::from)
        .toList();

    PageImpl<GetProductsResponse> getProductsResponses = new PageImpl<>(content, pageable,
        productsByCondition.getTotalElements());

    return PagedResponse.from(getProductsResponses);
  }


}
