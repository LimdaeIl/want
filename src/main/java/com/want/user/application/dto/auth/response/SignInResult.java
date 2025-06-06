package com.want.user.application.dto.auth.response;

import com.want.user.domain.user.User;
import org.springframework.http.ResponseCookie;

public record SignInResult(
    SignInResponse response,
    ResponseCookie cookie
) {
  public static SignInResult of(User user, String accessToken, ResponseCookie cookie) {
    return new SignInResult(SignInResponse.from(user, accessToken), cookie);
  }
}