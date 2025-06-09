package com.want.user.presentation;

import com.want.common.config.PagedResponse;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.common.response.ApiResponse;
import com.want.user.application.dto.auth.request.UserSearchCondition;
import com.want.user.application.dto.user.request.UpdateEmailRequest;
import com.want.user.application.dto.user.request.UpdateInfoRequest;
import com.want.user.application.dto.user.request.UpdatePasswordRequest;
import com.want.user.application.dto.user.request.UpdatePhoneRequest;
import com.want.user.application.dto.user.request.UpdateRoleRequest;
import com.want.user.application.dto.user.response.GetMeResponse;
import com.want.user.application.dto.user.response.GetUserResponse;
import com.want.user.application.dto.user.response.GetUsersResponse;
import com.want.user.application.dto.user.response.UpdateEmailResponse;
import com.want.user.application.dto.user.response.UpdateInfoResponse;
import com.want.user.application.dto.user.response.UpdatePhoneResponse;
import com.want.user.application.dto.user.response.UpdateRoleResponse;
import com.want.user.application.service.user.UserService;
import com.want.user.domain.user.UserSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CUSTOMER')")
  @GetMapping
  public ResponseEntity<ApiResponse<PagedResponse<GetUsersResponse>>> getUsers(
      @ParameterObject UserSearchCondition condition,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Direction.DESC),
          @SortDefault(sort = "id", direction = Direction.DESC)
      })
      Pageable page
  ) {
    PagedResponse<GetUsersResponse> response = userService.getUsers(condition, page);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(
            new ApiResponse<>(
                UserSuccessCode.USER_LIST_GET_SUCCESS.getCode(),
                UserSuccessCode.USER_LIST_GET_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @PatchMapping("/{id}/email")
  public ResponseEntity<ApiResponse<UpdateEmailResponse>> updateEmail(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long id,
      @RequestBody @Valid UpdateEmailRequest request
  ) {
    UpdateEmailResponse response = userService.updateEmail(userDetails, id, request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                UserSuccessCode.USER_EMAIL_UPDATE_SUCCESS.getCode(),
                UserSuccessCode.USER_EMAIL_UPDATE_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasRole('ADMIN') or #userDetails.id == #id")
  @PatchMapping("/{id}/password")
  public ResponseEntity<ApiResponse<Void>> updatePassword(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long id,
      @RequestBody @Valid UpdatePasswordRequest request
  ) {
    userService.updatePassword(userDetails, id, request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                UserSuccessCode.USER_PASSWORD_UPDATE_SUCCESS.getCode(),
                UserSuccessCode.USER_PASSWORD_UPDATE_SUCCESS.getMessage(),
                null
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @PatchMapping("/{id}/info")
  public ResponseEntity<ApiResponse<UpdateInfoResponse>> updateInfo(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long id,
      @RequestBody @Valid UpdateInfoRequest request
  ) {
    UpdateInfoResponse response = userService.updateInfo(userDetails, id, request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                UserSuccessCode.USER_UPDATE_SUCCESS.getCode(),
                UserSuccessCode.USER_UPDATE_SUCCESS.getMessage(),
            response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PatchMapping("/{id}/role")
  public ResponseEntity<ApiResponse<UpdateRoleResponse>> updateRole(
      @PathVariable Long id,
      @RequestBody @Valid UpdateRoleRequest request
  ) {
    UpdateRoleResponse response = userService.updateRole(id, request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                UserSuccessCode.USER_UPDATE_SUCCESS.getCode(),
                UserSuccessCode.USER_UPDATE_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @PatchMapping("/{id}/phone")
  public ResponseEntity<ApiResponse<UpdatePhoneResponse>> updatePhone(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long id,
      @RequestBody @Valid UpdatePhoneRequest request
  ) {
    UpdatePhoneResponse response = userService.updatePhone(userDetails, id, request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                UserSuccessCode.USER_UPDATE_SUCCESS.getCode(),
                UserSuccessCode.USER_UPDATE_SUCCESS.getMessage(),
                response
            )
        );
  }
}
