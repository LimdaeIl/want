package com.want.order.domain.entity;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

import com.want.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderSuccessCode implements SuccessCode {

  // ────────────── [주문 생성 관련] ──────────────
  ORDER_CREATE_SUCCESS(0, "주문이 성공적으로 생성되었습니다.", CREATED),

  // ────────────── [주문 조회 관련] ──────────────
  ORDER_GET_SUCCESS(0, "주문 조회에 성공했습니다.", OK),
  ORDER_LIST_GET_SUCCESS(0, "주문 목록 조회에 성공했습니다.", OK),

  // ────────────── [주문 취소 관련] ──────────────
  ORDER_CANCEL_SUCCESS(0, "주문이 성공적으로 취소되었습니다.", OK),

  // ────────────── [주문 수정 관련] ──────────────
  ORDER_STATUS_UPDATE(0, "주문 상태 수정에 성공했습니다.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}