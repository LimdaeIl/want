package com.want.order.domain.entity;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

  // ────────────── [주문 생성 관련] ──────────────
  ORDER_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "주문 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  ORDER_PRODUCT_EMPTY(HttpStatus.BAD_REQUEST.value(), "주문 상품이 비어 있습니다.", HttpStatus.BAD_REQUEST),
  ORDER_INVALID_TOTAL_AMOUNT(HttpStatus.BAD_REQUEST.value(), "주문 총액이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  ORDER_MINIMUM_AMOUNT_NOT_MET(HttpStatus.BAD_REQUEST.value(), "최소 주문 금액을 충족하지 못했습니다.", HttpStatus.BAD_REQUEST),
  ORDER_QUANTITY_EXCEEDS_STOCK(HttpStatus.BAD_REQUEST.value(), "상품 재고보다 많은 수량을 주문할 수 없습니다.", HttpStatus.BAD_REQUEST),
  ORDER_QUANTITY_INVALID_NEGATIVE(HttpStatus.BAD_REQUEST.value(), "상품 재고는 음수가 될 수 없습니다.", HttpStatus.BAD_REQUEST),


  // ────────────── [주문 조회 관련] ──────────────
  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 ID의 주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ORDER_ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "해당 주문에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),

  // ────────────── [주문 취소/수정 관련] ──────────────
  ORDER_CANCEL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "주문 취소에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST.value(), "이미 취소된 주문입니다.", HttpStatus.BAD_REQUEST),
  ORDER_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "해당 주문은 수정할 수 없습니다.", HttpStatus.FORBIDDEN),
  ORDER_STATUS_INVALID(HttpStatus.BAD_REQUEST.value(), "주문 상태가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [결제 관련] ──────────────
  PAYMENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "결제 처리에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST.value(), "결제 금액이 주문 금액과 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
  PAYMENT_METHOD_UNSUPPORTED(HttpStatus.BAD_REQUEST.value(), "지원하지 않는 결제 수단입니다.", HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}