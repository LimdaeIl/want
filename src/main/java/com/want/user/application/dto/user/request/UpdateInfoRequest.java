package com.want.user.application.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateInfoRequest(

    @NotBlank(message = "이름: 이름은 필수입니다.")
    @Size(max = 10, message = "이름: 이름은 10자 이하입니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9]+$",
        message = "이름: 한글, 영어 대소문자, 숫자만 입력 가능합니다."
    )
    String newName,
    String newProfileImage
) {
}
