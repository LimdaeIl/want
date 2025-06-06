package com.want.user.application.service;


import com.want.common.exception.CustomException;
import com.want.user.application.dto.request.SignupRequest;
import com.want.user.application.dto.response.SignupResponse;
import com.want.user.domain.auth.AuthErrorCode;
import com.want.user.domain.repository.UserRepository;
import com.want.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email)
        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND_BY_EMAIL));
  }

  private User findUserByPhone(String phone) {
    return userRepository.findUserByPhone(phone)
        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND_BY_PHONE));
  }

  private void existsUserByPhone(String phone) {
    if (userRepository.existsUserByPhone(phone)) {
      throw new CustomException(AuthErrorCode.USER_ALREADY_EXISTS);
    }
  }

  private void existsUserByEmail(String email) {
    if (userRepository.existsUserByEmail(email)) {
      throw new CustomException(AuthErrorCode.USER_ALREADY_EXISTS);
    }
  }

  @Override
  public SignupResponse signup(SignupRequest request) {
    existsUserByEmail(request.email());
    existsUserByPhone(request.phone());

    User buildUser = User.builder()
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .name(request.name())
        .phone(request.phone())
        .profileImage(request.profileImageUrl())
        .build();

    userRepository.save(buildUser);

    return SignupResponse.from(buildUser);
  }
}
