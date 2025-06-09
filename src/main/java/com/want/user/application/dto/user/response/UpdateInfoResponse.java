package com.want.user.application.dto.user.response;

import com.want.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateInfoResponse(
    Long id,
    String name,
    String profileImage
) {

  public static UpdateInfoResponse from(User user) {
    return UpdateInfoResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .profileImage(user.getProfileImage())
        .build();
  }
}
