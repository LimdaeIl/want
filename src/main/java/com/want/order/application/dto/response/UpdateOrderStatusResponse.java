package com.want.order.application.dto.response;

import com.want.order.domain.entity.Order;
import com.want.order.domain.entity.Status;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateOrderStatusResponse(
    UUID id,
    Status newStatus,
    LocalDateTime updatedAt,
    Long updatedBy
) {
  public static UpdateOrderStatusResponse from(Order order) {
    return UpdateOrderStatusResponse.builder()
        .id(order.getId())
        .newStatus(order.getStatus())
        .updatedAt(order.getUpdatedAt())
        .updatedBy(order.getUpdatedBy())
        .build();
  }
}
