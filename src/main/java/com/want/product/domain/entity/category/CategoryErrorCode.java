package com.want.product.domain.entity.category;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

  // ────────────── [카테고리 조회 관련] ──────────────
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 ID의 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // ────────────── [카테고리 등록 관련] ──────────────
  CATEGORY_NAME_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 존재하는 카테고리 이름입니다.", HttpStatus.CONFLICT),
  CATEGORY_SELF_PARENT(HttpStatus.BAD_REQUEST.value(), "자기 자신을 부모로 설정할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CATEGORY_PARENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "부모 카테고리를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [카테고리 수정/삭제 관련] ──────────────
  CATEGORY_HAS_CHILDREN(HttpStatus.BAD_REQUEST.value(), "하위 카테고리가 있어 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CATEGORY_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "카테고리 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  CATEGORY_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "카테고리 삭제에 실패했습니다.",
      HttpStatus.INTERNAL_SERVER_ERROR),
  CANNOT_SET_SELF_AS_PARENT(HttpStatus.BAD_REQUEST.value(), "자기 자신을 부모로 수정할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CANNOT_SET_DESCENDANT_AS_PARENT(HttpStatus.BAD_REQUEST.value(), "자신의 하위 카테고리를 부모 카테고리로 수정할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CATEGORY_NAME_BLACK(HttpStatus.BAD_REQUEST.value(), "카테고리 이름은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}