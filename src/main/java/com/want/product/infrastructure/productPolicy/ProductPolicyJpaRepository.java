package com.want.product.infrastructure.productPolicy;

import com.want.product.domain.entity.productPolicy.ProductPolicy;
import com.want.product.domain.repository.ProductPolicyRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPolicyJpaRepository extends JpaRepository<ProductPolicy, UUID>, ProductPolicyRepository {
}
