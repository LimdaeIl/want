package com.want.order.application.dto.response;

import com.want.order.domain.entity.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrdersResponse(
    UUID id,
    Long userId,
    String userName,
    Status status,
    String message,
    LocalDateTime createdAt,
    Long createdBy
) {}