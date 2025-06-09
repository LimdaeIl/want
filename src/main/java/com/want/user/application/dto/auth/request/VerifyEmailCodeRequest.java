package com.want.user.application.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyEmailCodeRequest(

    @NotBlank(message = "이메일: 이메일은 필수입니다.")
    @Email(message = "이메일: 유효하지 않은 이메일 형식입니다.")
    String email,

    @Min(value = 100000, message = "인증 코드: 6자리 숫자여야 합니다.")
    @Max(value = 999999, message = "인증 코드: 6자리 숫자여야 합니다.")
    @NotNull(message = "인증 코드: 필수입니다.")
    Integer code
) {
}
