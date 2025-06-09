package com.want.user.application.dto.user.response;

import com.want.user.domain.user.Role;
import java.time.LocalDateTime;

public record GetUsersResponse(
    Long id,
    String email,
    String name,
    String profileImage,
    Role role,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {
}
