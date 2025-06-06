package com.want.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(

    @NotBlank(message = "이메일: 이메일은 필수입니다.")
    @Email(message = "이메일: 유효하지 않은 이메일 형식입니다.")
    String email,

    @NotBlank(message = "비밀번호: 비밀번호는 필수입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%?&]{8,}$",
        message = "비밀번호: 비밀번호는 최소 8자 이상이며, 영문자, 숫자, 특수문자(@, $, !, %, ?, &)를 포함해야 합니다.")
    String password,

    @NotBlank(message = "이름: 이름은 필수입니다.")
    @Size(max = 10, message = "이름: 이름은 10자 이하입니다.") // 어느 조건이 틀렸는지 파악하기 용이!
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9]+$",
        message = "이름: 한글, 영어 대소문자, 숫자만 입력 가능합니다."
    )
    String name,

    @NotBlank(message = "휴대전화번호: 휴대전화번호는 필수입니다.")
    @Pattern(
        regexp = "^01[016789]\\d{7,8}$",
        message = "휴대전화번호: 숫자만 입력하며, 01012345678 형식이어야 합니다."
    )
    String phone,

    String profileImageUrl
) {
}
