package com.want.order.application.dto.request;

import java.util.List;
import java.util.UUID;


public record CreateOrderRequest(
    List<OrderProductRequest> products,
    String message
) {

  public record OrderProductRequest(
      UUID productId,
      Integer quantity
  ) {
  }
}
