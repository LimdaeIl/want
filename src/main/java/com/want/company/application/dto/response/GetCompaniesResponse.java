package com.want.company.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetCompaniesResponse(
    UUID id,
    String name,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy,
    LocalDateTime deletedAt,
    Long deletedBy
) {
}
