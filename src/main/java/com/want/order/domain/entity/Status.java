package com.want.order.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
  PENDING("주문 대기"),
  PAID("주문 완료"),
  SHIPPED("배송 시작"),
  DELIVERED("배송 완료"),
  CANCELLED("주문 취소");

  private final String name;

}
