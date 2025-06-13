package com.want.order.presentation;

import com.want.common.config.PagedResponse;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.order.application.dto.request.CreateOrderRequest;
import com.want.order.application.dto.request.OrderSearchCondition;
import com.want.order.application.dto.response.CreateOrderResponse;
import com.want.order.application.dto.response.GetOrderResponse;
import com.want.order.application.dto.response.GetOrdersResponse;
import com.want.order.application.service.OrderService;
import com.want.order.domain.entity.OrderSuccessCode;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GetOrderResponse>> getOrder(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable UUID id
  ) {
    GetOrderResponse response = orderService.getOrder(userDetails, id);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                OrderSuccessCode.ORDER_GET_SUCCESS.getCode(),
                OrderSuccessCode.ORDER_GET_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @GetMapping
  public ResponseEntity<ApiResponse<PagedResponse<GetOrdersResponse>>> getOrders(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @ParameterObject OrderSearchCondition condition,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Direction.DESC),
          @SortDefault(sort = "id", direction = Direction.DESC)
      })
      Pageable pageable
  ) {
    PagedResponse<GetOrdersResponse> response = orderService.getOrders(userDetails, condition, pageable);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                OrderSuccessCode.ORDER_LIST_GET_SUCCESS.getCode(),
                OrderSuccessCode.ORDER_LIST_GET_SUCCESS.getMessage(),
                response
            )
        );
  }

}
