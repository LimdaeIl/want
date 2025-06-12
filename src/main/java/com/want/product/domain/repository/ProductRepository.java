package com.want.product.domain.repository;

import com.want.product.domain.entity.product.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

  List<Product> findAllByIdIn(List<UUID> ids);

  Product save(Product product);

  boolean existsByName(String name);

  Optional<Product> findById(UUID id);
}
