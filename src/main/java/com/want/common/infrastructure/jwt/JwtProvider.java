package com.want.common.infrastructure.jwt;


import com.want.user.domain.user.User;

public interface JwtProvider {
  String createAccessToken(User user);

  String createRefreshToken(User user);

  long getRefreshTokenExpire();

  long getRemainingMillisByToken(String bearerToken);

  String getTokenId(String bearerToken);

  Long getUserIdByToken(String bearerToken);

  Long getUserId(String bearerToken);
  
  String getRole(String bearerToken);
}
