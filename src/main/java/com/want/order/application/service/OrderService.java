package com.want.order.application.service;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.order.application.dto.request.CreateOrderRequest;
import com.want.order.application.dto.response.CreateOrderResponse;

public interface OrderService {
  CreateOrderResponse createOrder(CustomUserDetails userDetails, CreateOrderRequest request);
}
