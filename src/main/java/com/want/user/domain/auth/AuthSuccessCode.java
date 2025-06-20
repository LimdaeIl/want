package com.want.user.domain.auth;

import static org.springframework.http.HttpStatus.OK;

import com.want.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {

  USER_SIGNUP_SUCCESS(0, "회원: 회원가입이 완료되었습니다.", OK),
  USER_LOGIN_SUCCESS(0, "회원: 로그인이 완료되었습니다.", OK),
  USER_LOGOUT_SUCCESS(0, "회원: 로그아웃이 완료되었습니다.", OK),
  USER_TOKEN_REISSUE_SUCCESS(0, "토큰: 재발급이 완료되었습니다.", OK),
  USER_TOKEN_VALIDATION_SUCCESS(0, "토큰: 유효한 토큰입니다.", OK),

  EMAIL_AVAILABLE(0, "이메일: 이메일 사용 가능합니다.", OK),
  EMAIL_DUPLICATE(0, "이메일: 이미 사용 중인 이메일입니다.", OK),
  EMAIL_VERIFICATION_SUCCESS(0, "이메일: 인증이 완료되었습니다.", OK),

  PHONE_AVAILABLE(0, "휴대전화번호: 사용 가능합니다.", OK),
  PHONE_DUPLICATE(0, "휴대전화번호: 이미 사용 중입니다.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}