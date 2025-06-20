package com.want.user.application.service.auth;

import com.want.user.application.dto.auth.request.SendEmailCodeRequest;
import com.want.user.application.dto.auth.request.SignInRequest;
import com.want.user.application.dto.auth.request.SignupRequest;
import com.want.user.application.dto.auth.response.ReissueResult;
import com.want.user.application.dto.auth.response.SignInResult;
import com.want.user.application.dto.auth.response.SignupResponse;
import com.want.user.application.dto.auth.request.VerifyEmailCodeRequest;
import jakarta.validation.Valid;

public interface AuthService {
  SignupResponse signup(@Valid SignupRequest request);

  SignInResult signIn(@Valid SignInRequest request);

  void signOut(String rt, String at);

  ReissueResult reissue(String rt);

  void sendEmailCode(SendEmailCodeRequest request);

  void verifyEmailCode(@Valid VerifyEmailCodeRequest request);
}
