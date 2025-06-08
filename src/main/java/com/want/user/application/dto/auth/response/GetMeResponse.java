package com.want.user.application.dto.auth.response;

import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record GetMeResponse(
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

  public static GetMeResponse from(User user) {
    return GetMeResponse.builder()
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