package com.want.user.application.dto.user.request;

import com.want.user.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원 권한 수정 요청")
public record UpdateRoleRequest(

    @Schema(description = "수정할 권한", example = "ROLE_MANAGER")
    @NotNull(message = "권한: 권한은 필수입니다.")
    Role newRole
) {

}
