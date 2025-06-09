package com.want.user.domain.user;

import com.want.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;



@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

  // ────────────── [회원 조회 관련] ──────────────
  USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND.value(), "해당 ID의 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND_BY_PHONE(HttpStatus.NOT_FOUND.value(), "해당 휴대전화번호의 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND.value(), "해당 이메일의 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "해당 회원 정보에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),
  USER_ROLE_INVALID(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 회원 권한입니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [회원 가입 관련] ──────────────
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "이미 존재하는 회원입니다.", HttpStatus.CONFLICT),
  USER_EMAIL_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
  USER_PHONE_DUPLICATE(HttpStatus.CONFLICT.value(), "이미 사용 중인 휴대전화번호입니다.", HttpStatus.CONFLICT),
  USER_SIGNUP_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원가입에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  EMAIL_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST.value(), "이메일 인증에 실패했습니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [회원 정보 수정 관련] ──────────────
  USER_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원 정보 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_EMAIL_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "본인의 이메일만 수정할 수 있습니다.", HttpStatus.FORBIDDEN),
  USER_PASSWORD_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "본인의 비밀번호만 수정할 수 있습니다.", HttpStatus.FORBIDDEN),
  USER_INFO_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "본인의 정보만 수정할 수 있습니다.", HttpStatus.FORBIDDEN),
  USER_PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED.value(), "기존 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
  USER_PASSWORD_SAME_AS_OLD(HttpStatus.BAD_REQUEST.value(), "이전과 동일한 비밀번호입니다.", HttpStatus.BAD_REQUEST),
  USER_PASSWORD_BLANK(HttpStatus.BAD_REQUEST.value(), "비밀번호는 필수 입력값입니다.", HttpStatus.BAD_REQUEST),
  USER_EMAIL_SAME_AS_OLD(HttpStatus.BAD_REQUEST.value(), "이전과 동일한 이메일입니다.", HttpStatus.BAD_REQUEST),
  USER_EMAIL_BLANK(HttpStatus.BAD_REQUEST.value(), "이메일은 필수 입력값입니다.", HttpStatus.BAD_REQUEST),
  USER_NAME_INVALID(HttpStatus.BAD_REQUEST.value(), "이름이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  USER_ROLE_INVALID_UPDATE(HttpStatus.BAD_REQUEST.value(), "수정하려는 권한이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  USER_PHONE_SAME_AS_OLD(HttpStatus.BAD_REQUEST.value(), "이전과 동일한 휴대전화번호입니다.", HttpStatus.BAD_REQUEST),
  USER_PHONE_BLANK(HttpStatus.BAD_REQUEST.value(), "휴대전화번호는 필수 입력값입니다.", HttpStatus.BAD_REQUEST),


  // ────────────── [로그인 관련] ──────────────
  LOGIN_INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST.value(), "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  LOGIN_PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST.value(), "비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

  // ────────────── [토큰 관련] ──────────────
  TOKEN_EXPIRED(HttpStatus.BAD_REQUEST.value(), "토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_INVALID_BEARER(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 Bearer 토큰입니다.", HttpStatus.BAD_REQUEST),
  REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  TOKEN_MALFORMED(HttpStatus.BAD_REQUEST.value(), "JWT 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_TAMPERED(HttpStatus.UNAUTHORIZED.value(), "JWT 서명이 위조되었거나 무결성이 손상되었습니다.", HttpStatus.UNAUTHORIZED),
  TOKEN_NOT_YET_VALID(HttpStatus.BAD_REQUEST.value(), "JWT 유효 시작 시간이 도래하지 않았습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_MISSING_USER_ID(HttpStatus.BAD_REQUEST.value(), "토큰에 사용자 ID가 없습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_MISSING_JWT_ID(HttpStatus.BAD_REQUEST.value(), "토큰에 JWT ID가 없습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_INVALID_SUBJECT(HttpStatus.BAD_REQUEST.value(), "JWT Subject가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_INVALID_TTL(HttpStatus.BAD_REQUEST.value(), "JWT TTL 설정이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  TOKEN_ALREADY_USED(HttpStatus.FORBIDDEN.value(), "이미 사용된 토큰입니다.", HttpStatus.FORBIDDEN);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}