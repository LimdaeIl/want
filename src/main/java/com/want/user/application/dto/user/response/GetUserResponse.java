package com.want.user.application.dto.user.response;

import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetUserResponse(
    Long id,
    String email,
    String name,
    String phone,
    String profileImage,
    Role role,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {

  public static GetUserResponse from(User user) {
    return GetUserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .phone(user.getPhone())
        .profileImage(user.getProfileImage())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .createdBy(user.getCreatedBy())
        .updatedAt(user.getUpdatedAt())
        .updatedBy(user.getUpdatedBy())
        .deletedAt(user.getDeletedAt())
        .deletedBy(user.getDeletedBy())
        .build();
  }

}
