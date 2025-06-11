package com.want.product.domain.repository;

import com.want.product.domain.entity.productPolicy.ProductPolicy;
import java.util.Optional;
import java.util.UUID;

public interface ProductPolicyRepository {

  boolean existsFindProductPolicyByName(String name);

  ProductPolicy save(ProductPolicy productPolicy);

  Optional<ProductPolicy> findById(UUID id);
}
