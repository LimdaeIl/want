package com.want.product.domain.repository;

import com.want.product.domain.entity.product.Product;
import java.util.List;
import java.util.UUID;

public interface ProductRepository {

  List<Product> findAllByIdIn(List<UUID> ids);

}
