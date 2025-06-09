package com.want.company.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record CompanySearchCondition(
    @Schema(description = "업체 ID")
    UUID id,

    @Schema(description = "업체 이름")
    @NotBlank(message = "업체 이름: 업체 이름은 필수입니다.")
    @Size(max = 30, message = "업체 이름: 업체 이름은 30자 이내여야 합니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9()\\[\\]/&.,\\-\\s]+$",
        message = "업체 이름: 업체 이름은 한글, 영문, 숫자, (), [], /, &, ., ,, -, 공백만 사용할 수 있습니다."
    )
    String name,

    @Schema(description = "삭제 여부")
    Boolean isDeleted,


    // ───── 생성일 필터 ─────
    @Schema(description = "업체 등록일 시작일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime createdFrom,

    @Schema(description = "업체 등록일 종료일 (YYYY-MM-DD)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime createdTo,

    @Schema(description = "업체 등록자 ID")
    Long createdBy,

    // ───── 수정일 필터 ─────
    @Schema(description = "업체 수정일 시작일 (YYYY-MM-DDTHH:MM:SS)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime updatedFrom,

    @Schema(description = "업체 수정일 종료일 (YYYY-MM-DDTHH:MM:SS)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime updatedTo,

    @Schema(description = "업체 수정자 ID")
    Long updatedBy,

    // ───── 삭제일 필터 ─────
    @Schema(description = "업체 삭제일 시작일 (YYYY-MM-DDTHH:MM:SS)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime deletedFrom,

    @Schema(description = "업체 삭제일 종료일 (YYYY-MM-DDTHH:MM:SS)")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime deletedTo,

    @Schema(description = "업체 삭제자 ID")
    Long deletedBy

) {
}
