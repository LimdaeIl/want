package com.want.order.application.dto.request;

import com.want.order.domain.entity.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSearchCondition(
    UUID id,
    Long userId,
    Status status,
    String message,
    LocalDateTime createdFrom,
    LocalDateTime createdTo,
    Long createdBy
) {
}
