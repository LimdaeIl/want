package com.want.order.domain.repository;

import com.want.order.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
  Order save(Order order);

  Optional<Order> findById(UUID id);

  Optional<Order> findWithUserAndProductsById(UUID id);
}
