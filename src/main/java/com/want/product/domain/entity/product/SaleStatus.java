package com.want.product.domain.entity.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SaleStatus {
  ACTIVE("활성화"),
  INACTIVE("비활성화"),
  SOLD_OUT("매진");

  private final String description;
}
