package com.want.product.domain.repository;

import com.want.product.domain.entity.productPolicy.ProductPolicy;

public interface ProductPolicyRepository {

  boolean existsFindProductPolicyByName(String name);

  ProductPolicy save(ProductPolicy productPolicy);
}
