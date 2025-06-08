package com.want.user.application.service.auth;


import com.want.common.exception.CustomException;
import com.want.common.infrastructure.jwt.JwtProvider;
import com.want.common.infrastructure.redis.RedisService;
import com.want.user.application.dto.auth.request.SignInRequest;
import com.want.user.application.dto.auth.request.SignupRequest;
import com.want.user.application.dto.auth.response.ReissueResult;
import com.want.user.application.dto.auth.response.SignInResult;
import com.want.user.application.dto.auth.response.SignupResponse;
import com.want.user.domain.auth.AuthErrorCode;
import com.want.user.domain.repository.UserRepository;
import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "AuthServiceImpl")
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final RedisService redisService;

  private User findUserById(Long userId) {
    return userRepository.findUserById(userId)
        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND_BY_ID));
  }

  private User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email)
        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND_BY_EMAIL));
  }

  private void existsUserByEmail(String email) {
    if (userRepository.existsUserByEmail(email)) {
      throw new CustomException(AuthErrorCode.USER_ALREADY_EXISTS);
    }
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

  private void validatePassword(String rawPassword, String encodedPassword) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new CustomException(AuthErrorCode.INVALID_SIGN_IN);
    }
  }

  @Transactional
  @Override
  public SignupResponse signup(SignupRequest request) {
    existsUserByEmail(request.email());
    existsUserByPhone(request.phone());

    User buildUser = User.builder()
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .name(request.name())
        .phone(request.phone())
        .profileImage(request.profileImage())
        .role(Role.ROLE_ADMIN) // TODO: 편의를 위한 ADMIN 자동 권한 부여
        .build();

    userRepository.save(buildUser);

    return SignupResponse.from(buildUser);
  }

  @Transactional
  @Override
  public SignInResult signIn(SignInRequest request) {
    User userByEmail = findUserByEmail(request.email());
    validatePassword(request.password(), userByEmail.getPassword());

    String accessToken = jwtProvider.createAccessToken(userByEmail);
    String refreshToken = jwtProvider.createRefreshToken(userByEmail);

    String rtKey = "RT:" + refreshToken;
    if (redisService.hasKey(rtKey)) {
      redisService.delete(rtKey);
    }
    redisService.set(rtKey, userByEmail.getId().toString(), jwtProvider.getRefreshTokenExpire(), TimeUnit.MILLISECONDS);

    ResponseCookie cookie = ResponseCookie
        .from("RT", refreshToken)
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .path("/")
        .maxAge(Duration.ofMillis(jwtProvider.getRefreshTokenExpire()))
        .build();

    return SignInResult.of(userByEmail, accessToken, cookie);
  }

  @Transactional
  @Override
  public void signOut(String rt, String at) {
    Long userIdByAT = jwtProvider.getUserId(at);
    Long userIdByRT = jwtProvider.getUserId(rt);

    findUserById(userIdByRT);

    if (!userIdByAT.equals(userIdByRT)) {
      throw new CustomException(AuthErrorCode.TAMPERED_TOKEN);
    }

    redisService.delete("RT:" + rt);

    long remainRT = jwtProvider.getRemainingMillisByToken(rt);
    long remainAT = jwtProvider.getRemainingMillisByToken(at);

    redisService.set("BL:" + rt, userIdByRT.toString(), remainRT, TimeUnit.MILLISECONDS);
    redisService.set("BL:" + at.substring(7), userIdByAT.toString(), remainAT, TimeUnit.MILLISECONDS);
  }

  @Transactional
  @Override
  public ReissueResult reissue(String rt) {
    if (rt == null) {
      throw new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    Long userId = jwtProvider.getUserId(rt);

    if (redisService.hasKey("BL:" + rt)) {
      throw new CustomException(AuthErrorCode.TOKEN_BLACKLISTED);
    }

    String savedUserIdByRT = redisService.get("RT:" + rt)
        .orElseThrow(() -> new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

    log.info("rt = {}", savedUserIdByRT);
    if (!savedUserIdByRT.equals(userId.toString())) {
      throw new CustomException(AuthErrorCode.TAMPERED_TOKEN);
    }

    User user = findUserById(userId);

    String newAT = jwtProvider.createAccessToken(user);
    String newRT = jwtProvider.createRefreshToken(user);

    redisService.delete("RT:" + rt);
    redisService.set("RT:" + newRT, user.getId().toString(), jwtProvider.getRefreshTokenExpire(),
        TimeUnit.MILLISECONDS);

    ResponseCookie cookie = ResponseCookie
        .from("RT", newRT)
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .path("/")
        .maxAge(Duration.ofMillis(jwtProvider.getRefreshTokenExpire()))
        .build();

    return ReissueResult.of(newAT, cookie);
  }
}
