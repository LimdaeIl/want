package com.want.product.domain.entity.productPolicy;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductPolicyErrorCode implements ErrorCode {

  // ────────────── [상품 정책 조회 관련] ──────────────
  POLICY_ID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 ID의 상품 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  POLICY_NAME_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 이름의 상품 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // ────────────── [상품 정책 등록 관련] ──────────────
  POLICY_NAME_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 존재하는 상품 정책 이름입니다.", HttpStatus.CONFLICT),
  POLICY_INVALID_CONDITION(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 정책 조건입니다.", HttpStatus.BAD_REQUEST),
  POLICY_APPLY_TARGET_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "정책이 적용될 대상을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [상품 정책 수정/삭제 관련] ──────────────
  POLICY_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "상품 정책 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  POLICY_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "상품 정책 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  POLICY_ALREADY_APPLIED(HttpStatus.BAD_REQUEST.value(), "이미 적용 중인 정책은 수정 또는 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_NAME_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 정책 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}