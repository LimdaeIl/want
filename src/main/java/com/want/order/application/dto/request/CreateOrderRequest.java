package com.want.order.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.UUID;


public record CreateOrderRequest(
    @NotEmpty
    @Valid
    List<OrderProductRequest> products,
    String message
) {

  public record OrderProductRequest(
      @NotNull
      UUID productId,

      @NotNull
      @Positive
      Integer quantity
  ) {
  }
}
