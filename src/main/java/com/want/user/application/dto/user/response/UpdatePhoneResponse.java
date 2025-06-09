package com.want.user.application.dto.user.response;

import com.want.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UpdatePhoneResponse(
    Long id,
    String phone
) {
  public static UpdatePhoneResponse from(User user) {
    return UpdatePhoneResponse.builder()
        .id(user.getId())
        .phone(user.getPhone())
        .build();
  }
}
