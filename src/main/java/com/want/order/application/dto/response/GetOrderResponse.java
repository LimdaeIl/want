package com.want.order.application.dto.response;

import com.want.order.domain.entity.Order;
import com.want.order.domain.entity.OrderProduct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetOrderResponse(
    UUID orderId,
    String message,
    String status,
    LocalDateTime createdAt,
    UserInfo user,
    List<OrderItem> items
) {
  public static GetOrderResponse from(Order order) {
    return new GetOrderResponse(
        order.getId(),
        order.getMessage(),
        order.getStatus().name(),
        order.getCreatedAt(),
        new UserInfo(order.getUser().getId(), order.getUser().getName()),
        order.getOrderProducts().stream()
            .map(OrderItem::from)
            .toList()
    );
  }

  public record UserInfo(
      Long userId,
      String name
  ) {}

  public record OrderItem(
      UUID productId,
      String productName,
      Integer quantity,
      Integer orderPrice
  ) {
    public static OrderItem from(OrderProduct op) {
      return new OrderItem(
          op.getProduct().getId(),
          op.getProduct().getName(),
          op.getOrderQuantity(),
          op.getOrderPrice()
      );
    }
  }
}