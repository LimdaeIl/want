package com.want.order.infrastructure.jpa;

import com.want.order.domain.entity.OrderProduct;
import com.want.order.domain.repository.OrderProductRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepositoryImpl extends JpaRepository<OrderProduct, UUID>, OrderProductRepository {
}
