package com.want.order.infrastructure.jpa;

import com.want.order.domain.entity.Order;
import com.want.order.domain.repository.OrderRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderJpaRepository extends JpaRepository<Order, UUID>, OrderRepository {

  @Query("""
          SELECT o
                FROM Order o
                      JOIN FETCH o.user u
                      JOIN FETCH o.orderProducts op
                      JOIN FETCH op.product p
          WHERE o.id = :id
      """)
  Optional<Order> findWithUserAndProductsById(@Param("id") UUID id);
}
