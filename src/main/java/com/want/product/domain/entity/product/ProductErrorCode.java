package com.want.product.domain.entity.product;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {

  // ────────────── [상품 조회] ──────────────
  PRODUCT_ID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 ID의 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  PRODUCT_NAME_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 이름의 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // ────────────── [상품 등록] ──────────────
  PRODUCT_NAME_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 존재하는 상품 이름입니다.", HttpStatus.CONFLICT),
  PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "상품에 대한 카테고리를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "상품에 대한 회사를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_POLICY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "상품 정책을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

  // ───── [필드 유효성: 이름, 설명, 가격, 수량 등] ─────
  PRODUCT_NAME_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_NAME_LENGTH_INVALID(HttpStatus.BAD_REQUEST.value(), "상품 이름은 100자 이내여야 합니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_DESCRIPTION_BLANK(HttpStatus.BAD_REQUEST.value(), "상품 설명은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_DESCRIPTION_LENGTH_INVALID(HttpStatus.BAD_REQUEST.value(), "상품 설명은 1000자 이내여야 합니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_PRICE_INVALID(HttpStatus.BAD_REQUEST.value(), "상품 가격은 양수여야 합니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_QUANTITY_INVALID(HttpStatus.BAD_REQUEST.value(), "상품 수량은 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_THUMBNAIL_BLANK(HttpStatus.BAD_REQUEST.value(), "썸네일은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_SALE_STATUS_BLANK(HttpStatus.BAD_REQUEST.value(), "판매 상태는 필수 항목입니다.", HttpStatus.BAD_REQUEST),

  // ───── [상태 및 논리 검사] ─────
  PRODUCT_ALREADY_SOLD_OUT(HttpStatus.BAD_REQUEST.value(), "품절된 상품입니다.", HttpStatus.BAD_REQUEST),
  PRODUCT_INACTIVE(HttpStatus.BAD_REQUEST.value(), "비활성화된 상품은 사용할 수 없습니다.", HttpStatus.BAD_REQUEST),

  // ───── [수정/삭제 실패] ─────
  PRODUCT_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "상품 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "상품 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}