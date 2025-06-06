package com.want.user.application.dto.auth.response;

import com.want.user.domain.user.User;

public record SignInResponse(
    Long id,
    String accessToken
) {
  public static SignInResponse from(User user, String accessToken) {
    return new SignInResponse(user.getId(), accessToken);
  }
}