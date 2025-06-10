package com.want.product.domain.entity.category;

import com.want.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategorySuccessCode implements SuccessCode {

  // ────────────── [카테고리 등록/조회/수정/삭제] ──────────────
  CATEGORY_CREATED(HttpStatus.CREATED.value(), "카테고리가 성공적으로 생성되었습니다.", HttpStatus.CREATED),
  CATEGORY_FETCHED(HttpStatus.OK.value(), "카테고리 정보를 성공적으로 조회했습니다.", HttpStatus.OK),
  CATEGORY_UPDATED(HttpStatus.OK.value(), "카테고리 정보가 성공적으로 수정되었습니다.", HttpStatus.OK),
  CATEGORY_DELETED(HttpStatus.OK.value(), "카테고리가 성공적으로 삭제되었습니다.", HttpStatus.OK),
  CATEGORY_LIST_FETCHED(HttpStatus.OK.value(), "카테고리 목록을 성공적으로 조회했습니다.", HttpStatus.OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}