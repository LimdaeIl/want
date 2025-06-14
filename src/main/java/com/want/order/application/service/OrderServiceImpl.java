package com.want.order.application.service;

import com.want.common.config.PagedResponse;
import com.want.common.exception.CustomException;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.order.application.dto.request.CreateOrderRequest;
import com.want.order.application.dto.request.CreateOrderRequest.OrderProductRequest;
import com.want.order.application.dto.request.OrderSearchCondition;
import com.want.order.application.dto.request.UpdateOrderStatusRequest;
import com.want.order.application.dto.response.CreateOrderResponse;
import com.want.order.application.dto.response.GetOrderResponse;
import com.want.order.application.dto.response.GetOrdersResponse;
import com.want.order.application.dto.response.UpdateOrderStatusResponse;
import com.want.order.domain.entity.Order;
import com.want.order.domain.entity.OrderErrorCode;
import com.want.order.domain.entity.OrderProduct;
import com.want.order.domain.entity.Status;
import com.want.order.domain.repository.OrderQuerydslRepository;
import com.want.order.domain.repository.OrderRepository;
import com.want.product.domain.entity.product.Product;
import com.want.product.domain.entity.product.ProductErrorCode;
import com.want.product.domain.repository.ProductRepository;
import com.want.user.domain.repository.UserRepository;
import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import com.want.user.domain.user.UserErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OrderQuerydslRepository orderQuerydslRepository;

  private User findUserById(Long id) {
    return userRepository.findUserById(id)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  private List<OrderProduct> createOrderProducts(CreateOrderRequest request) {
    List<OrderProduct> orderProducts = new ArrayList<>();

    for (OrderProductRequest item : request.products()) {
      Product product = productRepository.findById(item.productId())
          .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_ID_NOT_FOUND));

      OrderProduct orderProduct = OrderProduct.validateAndCreate(product, item.quantity());
      orderProducts.add(orderProduct);
    }

    return orderProducts;
  }

  private Order createOrderEntity(User user, String message, List<OrderProduct> orderProducts) {
    Order order = Order.builder()
        .user(user)
        .status(Status.PENDING)
        .message(message)
        .build();

    for (OrderProduct op : orderProducts) {
      order.addOrderProduct(op);
    }

    return order;
  }

  private Order findOrderById(UUID id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new CustomException(OrderErrorCode.ORDER_NOT_FOUND));
  }

  @Transactional
  @Override
  public CreateOrderResponse createOrder(CustomUserDetails userDetails, CreateOrderRequest request) {
    // 회원 확인
    User user = findUserById(userDetails.id());

    // 주문상품 생성
    List<OrderProduct> orderProducts = createOrderProducts(request);

    // 주문 생성
    Order order = createOrderEntity(user, request.message(), orderProducts);

    // 주문 저장
    Order savedOrder = orderRepository.save(order);

    return CreateOrderResponse.from(savedOrder);
  }

  @Transactional(readOnly = true)
  @Override
  public GetOrderResponse getOrder(CustomUserDetails userDetails, UUID id) {
    Order order = orderRepository.findWithUserAndProductsById(id)
        .orElseThrow(() -> new CustomException(OrderErrorCode.ORDER_NOT_FOUND));

    return GetOrderResponse.from(order);
  }

  @Transactional
  @Override
  public PagedResponse<GetOrdersResponse> getOrders(
      CustomUserDetails userDetails,
      OrderSearchCondition condition,
      Pageable pageable) {

    Page<GetOrdersResponse> ordersByCondition = orderQuerydslRepository.findOrdersByCondition(condition, pageable);

    return PagedResponse.from(ordersByCondition);
  }

  @Transactional
  @Override
  public UpdateOrderStatusResponse updateOrderStatus(CustomUserDetails userDetails, UpdateOrderStatusRequest request,
                                                     UUID id) {
    Order orderById = findOrderById(id);

    if (userDetails.role() != Role.ROLE_ADMIN && !userDetails.id().equals(orderById.getUser().getId())) {
      throw new CustomException(OrderErrorCode.ORDER_UPDATE_FORBIDDEN);
    }

    orderById.updateStatus(request.newStatus());

    return UpdateOrderStatusResponse.from(orderById);
  }
}
