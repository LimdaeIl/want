package com.want.product.infrastructure.product;

import com.want.product.domain.entity.product.Product;
import com.want.product.domain.repository.ProductRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, UUID>, ProductRepository {
}
