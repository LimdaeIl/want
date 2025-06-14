package com.want.order.application.dto.response;

import com.want.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeleteOrderResponse(
    UUID id,
    LocalDateTime deletedAt,
    Long deletedBy

) {
  public static DeleteOrderResponse from(Order order) {
    return DeleteOrderResponse.builder()
        .id(order.getId())
        .deletedAt(order.getDeletedAt())
        .deletedBy(order.getDeletedBy())
        .build();
  }
}
