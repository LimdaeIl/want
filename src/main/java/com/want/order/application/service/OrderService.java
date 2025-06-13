package com.want.order.application.service;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.order.application.dto.request.CreateOrderRequest;
import com.want.order.application.dto.response.CreateOrderResponse;
import com.want.order.application.dto.response.GetOrderResponse;
import java.util.UUID;

public interface OrderService {
  CreateOrderResponse createOrder(CustomUserDetails userDetails, CreateOrderRequest request);

  GetOrderResponse getOrder(CustomUserDetails userDetails, UUID id);
}
