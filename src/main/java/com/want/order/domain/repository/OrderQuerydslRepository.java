package com.want.order.domain.repository;

import com.want.order.application.dto.request.OrderSearchCondition;
import com.want.order.application.dto.response.GetOrdersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQuerydslRepository {

  Page<GetOrdersResponse> findOrdersByCondition(OrderSearchCondition condition, Pageable pageable);

}
