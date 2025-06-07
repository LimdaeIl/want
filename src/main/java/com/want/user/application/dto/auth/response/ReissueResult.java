package com.want.user.application.dto.auth.response;

import org.springframework.http.ResponseCookie;

public record ReissueResult(
    String accessToken,
    ResponseCookie refreshTokenCookie
) {

  public static ReissueResult of(String accessToken, ResponseCookie refreshTokenCookie) {
    return new ReissueResult(accessToken, refreshTokenCookie);
  }
}