package com.want.common.infrastructure.jwt;


import com.want.user.domain.user.Role;

public interface JwtProvider {
  String createAccessToken(Long userId, Role role);

  String createRefreshToken(Long userId);

  long getRefreshTokenExpire();

  long getRemainingMillisByToken(String bearerToken);

  String getTokenId(String bearerToken);

  Long getUserIdByToken(String bearerToken);

  Long getUserId(String bearerToken);

  String getEmail(String bearerToken);

  String getRole(String bearerToken);
}
