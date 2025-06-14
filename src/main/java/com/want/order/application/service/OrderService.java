package com.want.order.application.service;

import com.want.common.config.PagedResponse;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.order.application.dto.request.CreateOrderRequest;
import com.want.order.application.dto.request.OrderSearchCondition;
import com.want.order.application.dto.request.UpdateOrderStatusRequest;
import com.want.order.application.dto.response.CreateOrderResponse;
import com.want.order.application.dto.response.DeleteOrderResponse;
import com.want.order.application.dto.response.GetOrderResponse;
import com.want.order.application.dto.response.GetOrdersResponse;
import com.want.order.application.dto.response.UpdateOrderStatusResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface OrderService {
  CreateOrderResponse createOrder(CustomUserDetails userDetails, CreateOrderRequest request);

  GetOrderResponse getOrder(CustomUserDetails userDetails, UUID id);

  PagedResponse<GetOrdersResponse> getOrders(CustomUserDetails userDetails, OrderSearchCondition condition,
                                             Pageable pageable);

  UpdateOrderStatusResponse updateOrderStatus(CustomUserDetails userDetails, UpdateOrderStatusRequest request, UUID id);

  DeleteOrderResponse deleteOrder(CustomUserDetails userDetails, UUID id);
}
