package com.want.order.application.dto.request;

import com.want.order.domain.entity.Status;

public record UpdateOrderStatusRequest(
    Status newStatus
) {
}
