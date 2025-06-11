package com.want.product.domain.entity.productPolicy;

import com.want.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiscountType {
  REGULAR("할인 없음") {
    @Override
    public void validateValue(Integer value) {
      if (value == null) {
        throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_BLANK);
      }
      if (value != 0) {
        throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_BLANK);
      }
    }
  },
  FIXED("정액 할인") {
    @Override
    public void validateValue(Integer value) {
      if (value == null) {
        throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_BLANK);
      }
      if (value < 0) {
        throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_INVALID);
      }
    }
  },
  PERCENTAGE("정률 할인") {
    @Override
    public void validateValue(Integer value) {
      if (value == null) {
        throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_BLANK);
      }
      if (value < 0 || value > 100) {
        throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_INVALID);
      }
    }
  };

  private final String description;

  public abstract void validateValue(Integer value);
}