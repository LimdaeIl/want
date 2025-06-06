package com.want.user.application.dto.response;

import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SignupResponse(
    Long id,
    String email,
    String name,
    String phone,
    String profileImage,
    Role role
) {

  public static SignupResponse from(User user) {
    return SignupResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .phone(user.getPhone())
        .profileImage(user.getProfileImage())
        .role(user.getRole())
        .build();
  }

}
