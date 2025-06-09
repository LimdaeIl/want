package com.want.user.application.dto.user.response;

import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateRoleResponse(
    Long id,
    Role role
) {
  public static UpdateRoleResponse from(User user) {
    return UpdateRoleResponse.builder()
        .id(user.getId())
        .role(user.getRole())
        .build();
  }
}
