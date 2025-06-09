package com.want.user.application.dto.user.response;

import com.want.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateEmailResponse(
    Long id,
    String email
) {
  public static UpdateEmailResponse from(User userById) {
    return UpdateEmailResponse.builder()
        .id(userById.getId())
        .email(userById.getEmail())
        .build();
  }
}
