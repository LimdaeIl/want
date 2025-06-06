package com.want.user.presentation;


import com.want.common.response.ApiResponse;
import com.want.user.application.dto.request.SignupRequest;
import com.want.user.application.dto.response.SignupResponse;
import com.want.user.application.service.AuthService;
import com.want.user.domain.auth.AuthSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<SignupResponse>> signup(
      @RequestBody @Valid SignupRequest request
  ) {
    SignupResponse response = authService.signup(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
            AuthSuccessCode.USER_SIGNUP_SUCCESS.getCode(),
            AuthSuccessCode.USER_SIGNUP_SUCCESS.getMessage(),
            response
        ));
  }
}
