package com.want.company.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCompanyRequest(

    @NotBlank(message = "업체 이름: 업체 이름은 필수입니다.")
    @Size(max = 30, message = "회사 이름: 업체 이름은 30자 이내여야 합니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9()\\[\\]/&.,\\-\\s]+$",
        message = "업체 이름: 업체 이름은 한글, 영문, 숫자, (), [], /, &, ., ,, -, 공백만 사용할 수 있습니다."
    )
    String name

) {
}