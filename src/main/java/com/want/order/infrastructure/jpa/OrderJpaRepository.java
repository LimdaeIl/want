package com.want.order.infrastructure.jpa;

import com.want.order.domain.entity.Order;
import com.want.order.domain.repository.OrderRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID>, OrderRepository {
}
