package com.want.user.presentation;


import com.want.common.response.ApiResponse;
import com.want.user.application.dto.auth.request.SignInRequest;
import com.want.user.application.dto.auth.request.SignupRequest;
import com.want.user.application.dto.auth.response.ReissueResponse;
import com.want.user.application.dto.auth.response.ReissueResult;
import com.want.user.application.dto.auth.response.SignInResponse;
import com.want.user.application.dto.auth.response.SignInResult;
import com.want.user.application.dto.auth.response.SignupResponse;
import com.want.user.application.service.auth.AuthService;
import com.want.user.domain.auth.AuthSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "AuthController")
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

  @PostMapping("/sign-in")
  public ResponseEntity<ApiResponse<SignInResponse>> signIn(
      @RequestBody @Valid SignInRequest request) {
    SignInResult result = authService.signIn(request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .header("Set-Cookie", result.cookie().toString())
        .body(new ApiResponse<>(
            AuthSuccessCode.USER_LOGIN_SUCCESS.getCode(),
            AuthSuccessCode.USER_LOGIN_SUCCESS.getMessage(),
            result.response()
        ));
  }

  @PostMapping("/sign-out")
  public ResponseEntity<ApiResponse<Void>> signOut(
      @CookieValue(value = "RT", required = false) String rt,
      @RequestHeader(value = "Authorization") String at
  ) {
    authService.signOut(rt, at);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/reissue")
  public ResponseEntity<ApiResponse<ReissueResponse>> reissue(
      @CookieValue(value = "RT", required = false) String rt
  ) {
    ReissueResult result = authService.reissue(rt);
    ReissueResponse response = ReissueResponse.from(result);

    return ResponseEntity
        .status(HttpStatus.OK)
        .header("Set-Cookie", result.refreshTokenCookie().toString())
        .body(new ApiResponse<>(
                AuthSuccessCode.USER_TOKEN_REISSUE_SUCCESS.getCode(),
                AuthSuccessCode.USER_TOKEN_REISSUE_SUCCESS.getMessage(),
                response
            )
        );
  }
}
