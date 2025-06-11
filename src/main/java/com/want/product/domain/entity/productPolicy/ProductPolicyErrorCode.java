package com.want.product.domain.entity.productPolicy;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductPolicyErrorCode implements ErrorCode {

  // ────────────── [상품 정책 조회] ──────────────
  POLICY_ID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 ID의 상품 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  POLICY_NAME_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 이름의 상품 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // ────────────── [상품 정책 등록] ──────────────
  POLICY_NAME_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 존재하는 상품 정책 이름입니다.", HttpStatus.CONFLICT),
  POLICY_APPLY_TARGET_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "정책이 적용될 대상을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

  // ───── [필드 유효성: 이름, 설명, 값] ─────
  POLICY_NAME_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 정책 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_DESCRIPTION_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 정책 설명은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_VALUE_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 정책 값은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_VALUE_NEGATIVE(HttpStatus.BAD_REQUEST.value(), "상품 정책 값은 음수일 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_VALUE_INVALID(HttpStatus.BAD_REQUEST.value(), "상품 정책 값은 0 이상 100 이하의 숫자만 가능합니다.", HttpStatus.BAD_REQUEST),
  POLICY_MIN_PURCHASE_INVALID(HttpStatus.BAD_REQUEST.value(), "최소 구매 금액은 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
  POLICY_MIN_PURCHASE_AMOUNT_BLANK(HttpStatus.BAD_REQUEST.value(), "최소 구매 금액은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_MIN_PURCHASE_AMOUNT_NEGATIVE(HttpStatus.BAD_REQUEST.value(), "최소 구매 금액은 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),

  // ───── [시간 유효성 검사] ─────
  POLICY_STARTED_AT_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 정책 시작일자는 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_ENDED_AT_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 정책 종료일자는 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_DATE_INVALID(HttpStatus.BAD_REQUEST.value(), "종료일은 시작일보다 이후여야 합니다.", HttpStatus.BAD_REQUEST),
  POLICY_START_DATE_IN_PAST(HttpStatus.BAD_REQUEST.value(), "시작일은 현재 시간보다 이후여야 합니다.", HttpStatus.BAD_REQUEST),
  POLICY_PERIOD_OVERLAP(HttpStatus.CONFLICT.value(), "동일한 회사에 대해 중복된 기간의 정책이 존재합니다.", HttpStatus.CONFLICT),


  // ───── [상태 및 논리 검사] ─────
  POLICY_ALREADY_APPLIED(HttpStatus.BAD_REQUEST.value(), "이미 적용 중인 정책은 수정 또는 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),
  POLICY_INVALID_CONDITION(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 정책 조건입니다.", HttpStatus.BAD_REQUEST),
  POLICY_INACTIVE(HttpStatus.BAD_REQUEST.value(), "비활성화된 정책은 사용할 수 없습니다.", HttpStatus.BAD_REQUEST),

  // ───── [수정/삭제 실패] ─────
  POLICY_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "상품 정책 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  POLICY_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "상품 정책 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}