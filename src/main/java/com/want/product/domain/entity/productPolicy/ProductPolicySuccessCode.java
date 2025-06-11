package com.want.product.domain.entity.productPolicy;

import com.want.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductPolicySuccessCode implements SuccessCode {

  // ────────────── [상품 정책 등록/조회/수정/삭제] ──────────────
  POLICY_CREATED(HttpStatus.CREATED.value(), "상품 정책이 성공적으로 생성되었습니다.", HttpStatus.CREATED),
  POLICY_FETCHED(HttpStatus.OK.value(), "상품 정책 정보를 성공적으로 조회했습니다.", HttpStatus.OK),
  POLICY_UPDATED(HttpStatus.OK.value(), "상품 정책 정보가 성공적으로 수정되었습니다.", HttpStatus.OK),
  POLICY_DELETED(HttpStatus.OK.value(), "상품 정책이 성공적으로 삭제되었습니다.", HttpStatus.OK),
  POLICY_LIST_FETCHED(HttpStatus.OK.value(), "상품 정책 목록을 성공적으로 조회했습니다.", HttpStatus.OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}