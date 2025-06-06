package com.want.user.application.service;

import com.want.user.application.dto.request.SignupRequest;
import com.want.user.application.dto.response.SignupResponse;
import jakarta.validation.Valid;

public interface AuthService {
  SignupResponse signup(@Valid SignupRequest request);
}
