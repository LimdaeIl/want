package com.want.user.presentation;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.user.application.dto.auth.response.GetMeResponse;
import com.want.user.application.dto.auth.response.GetUserResponse;
import com.want.user.application.service.user.UserService;
import com.want.user.domain.user.UserSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GetUserResponse>> getUser(
      @PathVariable Long id
  ) {
    GetUserResponse response = userService.getUser(id);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(
            new ApiResponse<>(
                UserSuccessCode.USER_GET_SUCCESS.getCode(),
                UserSuccessCode.USER_GET_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<GetMeResponse>> getMe(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    GetMeResponse response = userService.getMe(userDetails);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(
            new ApiResponse<>(
                UserSuccessCode.USER_GET_SUCCESS.getCode(),
                UserSuccessCode.USER_GET_SUCCESS.getMessage(),
                response
            )
        );
  }
  
}
