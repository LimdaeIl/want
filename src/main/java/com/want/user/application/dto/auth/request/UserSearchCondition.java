package com.want.user.application.dto.auth.request;

import com.want.user.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Schema(description = "회원 검색 조건")
public record UserSearchCondition(
    @Schema(description = "회원 ID")
    Long id,

    @Schema(description = "이메일 (LIKE 검색)")
    String email,

    @Schema(description = "이름 (LIKE 검색)")
    String name,

    @Schema(description = "전화번호 (LIKE 검색)")
    String phone,

    @Schema(description = "회원 권한")
    Role role,

    @Schema(description = "삭제 여부")
    Boolean isDeleted,

    // ───── 생성일 필터 ─────
    @Schema(description = "회원 가입일 시작일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime createdFrom,

    @Schema(description = "회원 가입일 종료일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime createdTo,

    @Schema(description = "회원 등록자 ID")
    Long createdBy,

    // ───── 수정일 필터 ─────
    @Schema(description = "회원 수정일 시작일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime updatedFrom,

    @Schema(description = "회원 수정일 종료일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime updatedTo,

    @Schema(description = "회원 수정자 ID")
    Long updatedBy,

    // ───── 삭제일 필터 ─────
    @Schema(description = "회원 삭제일 시작일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime deletedFrom,

    @Schema(description = "회원 삭제일 종료일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime deletedTo,

    @Schema(description = "회원 삭제자 ID")
    Long deletedBy
) {
}
