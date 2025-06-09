package com.want.user.application.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePhoneRequest(
    @NotBlank(message = "휴대전화번호: 휴대전화번호는 필수입니다.")
    @Pattern(
        regexp = "^01[016789]\\d{7,8}$",
        message = "휴대전화번호: 숫자만 입력하며, 01012345678 형식이어야 합니다."
    )
    String newPhone
) {
}
