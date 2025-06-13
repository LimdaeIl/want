package com.want.order.presentation;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.order.application.dto.request.CreateOrderRequest;
import com.want.order.application.dto.response.CreateOrderResponse;
import com.want.order.application.service.OrderService;
import com.want.order.domain.entity.OrderSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

  private final OrderService orderService;

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @PostMapping
  public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid CreateOrderRequest request
  ) {
    CreateOrderResponse response = orderService.createOrder(userDetails, request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
                OrderSuccessCode.ORDER_CREATE_SUCCESS.getCode(),
                OrderSuccessCode.ORDER_CREATE_SUCCESS.getMessage(),
                response
            )
        );
  }

}
