package com.want.order.domain.repository;

import com.want.order.domain.entity.Order;

public interface OrderRepository {
  Order save(Order order);
}
