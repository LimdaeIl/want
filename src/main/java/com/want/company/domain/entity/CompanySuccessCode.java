package com.want.company.domain.entity;

import com.want.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanySuccessCode implements SuccessCode {

  // ────────────── [회사 조회 관련] ──────────────
  COMPANY_GET_SUCCESS(0, "회사 조회에 성공했습니다.", HttpStatus.OK),
  COMPANIES_GET_SUCCESS(0, "회사 목록 조회에 성공했습니다.", HttpStatus.OK),

  // ────────────── [회사 생성/수정/삭제 관련] ──────────────
  COMPANY_CREATE_SUCCESS(0, "회사 생성에 성공했습니다.", HttpStatus.CREATED),
  COMPANY_UPDATE_SUCCESS(0, "회사 정보 수정에 성공했습니다.", HttpStatus.OK),
  COMPANY_DELETE_SUCCESS(0, "회사 삭제에 성공했습니다.", HttpStatus.OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}