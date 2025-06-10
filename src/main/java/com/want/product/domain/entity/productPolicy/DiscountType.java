package com.want.product.domain.entity.productPolicy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiscountType {
  REGULAR("할인 없음"),
  FIXED("정액 할인"),
  AMOUNT("금액 할인"),
  PERCENTAGE("정률 할인");

  private final String description;
}
