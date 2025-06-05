package com.want.common.infrastructure.jwt;


import static com.want.user.domain.auth.AuthErrorCode.INVALID_BEARER_TOKEN;
import static com.want.user.domain.auth.AuthErrorCode.MALFORMED_TOKEN;
import static com.want.user.domain.auth.AuthErrorCode.TAMPERED_TOKEN;
import static com.want.user.domain.auth.AuthErrorCode.TOKEN_EXPIRED;
import static com.want.user.domain.auth.AuthErrorCode.TOKEN_TOO_EARLY;

import com.want.common.exception.CustomException;
import com.want.user.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class JwtProviderImpl implements JwtProvider {

  private final Key accessKey;
  private final Key refreshKey;
  private final SecretKey secretKey;

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${jwt.access.expire}")
  private long accessTokenExpire;

  @Value("${jwt.refresh.expire}")
  private long refreshTokenExpire;

  private static final String PREFIX_BEARER = "Bearer ";

  public JwtProviderImpl(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.accessKey = Keys.hmacShaKeyFor(keyBytes);
    this.refreshKey = Keys.hmacShaKeyFor(keyBytes);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }


  @Override
  public String createAccessToken(Long userId, Role role) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(userId.toString())
        .claim("USER_ROLE", role)
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + accessTokenExpire))
        .signWith(secretKey)
        .compact();
  }

  @Override
  public String refreshAccessToken(Long userId) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(userId.toString())
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
        .signWith(secretKey)
        .compact();
  }

  @Override
  public long getRemainingMillisByToken(String bearerToken) {
    Claims claims = extractClaims(bearerToken);
    return claims.getExpiration().getTime() - System.currentTimeMillis();
  }


  @Override
  public String getTokenId(String bearerToken) {
    Claims claims = extractClaims(bearerToken);
    return claims.getId();
  }

  @Override
  public Long getUserId(String bearerToken) {
    return extractClaims(bearerToken).get("userId", Long.class);
  }

  @Override
  public String getEmail(String bearerToken) {
    return extractClaims(bearerToken).getSubject();
  }

  @Override
  public String getRole(String bearerToken) {
    return extractClaims(bearerToken).get("role", String.class);
  }

  @Override
  public Long getUserIdByToken(String bearerToken) {
    Claims claims = extractClaims(bearerToken);

    return Long.parseLong(claims.getSubject());
  }


  private Claims extractClaims(String bearerToken) {
    if (bearerToken.isBlank() || !bearerToken.startsWith(PREFIX_BEARER)) {
      throw new CustomException(INVALID_BEARER_TOKEN);
    }

    String token = bearerToken.substring(PREFIX_BEARER.length());

    try {
      return Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (ExpiredJwtException e) {
      throw new CustomException(TOKEN_EXPIRED);
    } catch (MalformedJwtException e) {
      throw new CustomException(MALFORMED_TOKEN);
    } catch (SignatureException e) {
      throw new CustomException(TAMPERED_TOKEN);
    } catch (PrematureJwtException e) {
      throw new CustomException(TOKEN_TOO_EARLY);
    } catch (JwtException e) {
      throw new CustomException(INVALID_BEARER_TOKEN);
    }
  }


}
