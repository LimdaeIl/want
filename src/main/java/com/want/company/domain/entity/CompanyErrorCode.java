package com.want.company.domain.entity;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyErrorCode implements ErrorCode {

  // ────────────── [회사 조회 관련] ──────────────
  COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "회사를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  COMPANY_NAME_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 존재하는 회사명입니다.", HttpStatus.CONFLICT),
  COMPANY_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "회사에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),

  // ────────────── [회사 등록/수정 관련] ──────────────
  COMPANY_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회사 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  COMPANY_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회사 정보 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_COMPANY_NAME(HttpStatus.BAD_REQUEST.value(), "회사명이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [회사 삭제 관련] ──────────────
  COMPANY_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회사 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  COMPANY_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 회사입니다.", HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}