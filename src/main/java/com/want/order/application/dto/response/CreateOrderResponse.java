package com.want.order.application.dto.response;

import com.want.order.domain.entity.Order;
import com.want.order.domain.entity.OrderProduct;
import java.util.List;
import java.util.UUID;

public record CreateOrderResponse(
    UUID orderId,
    List<OrderItemResponse> items,
    String message,
    String status
) {
  public static CreateOrderResponse from(Order order) {
    return new CreateOrderResponse(
        order.getId(),
        order.getOrderProducts().stream()
            .map(OrderItemResponse::from)
            .toList(),
        order.getMessage(),
        order.getStatus().name()
    );
  }

  public record OrderItemResponse(
      UUID productId,
      String productName,
      Integer quantity,
      Integer price
  ) {
    public static OrderItemResponse from(OrderProduct op) {
      return new OrderItemResponse(
          op.getProduct().getId(),
          op.getProduct().getName(),
          op.getOrderQuantity(),
          op.getOrderPrice()
      );
    }
  }
}
