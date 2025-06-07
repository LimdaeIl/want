package com.want.common.infrastructure.jwt;


import static com.want.user.domain.auth.AuthErrorCode.INVALID_BEARER_TOKEN;
import static com.want.user.domain.auth.AuthErrorCode.MALFORMED_TOKEN;
import static com.want.user.domain.auth.AuthErrorCode.TAMPERED_TOKEN;
import static com.want.user.domain.auth.AuthErrorCode.TOKEN_EXPIRED;
import static com.want.user.domain.auth.AuthErrorCode.TOKEN_TOO_EARLY;

import com.want.common.exception.CustomException;
import com.want.user.domain.auth.AuthErrorCode;
import com.want.user.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j(topic = "JwtProviderImpl")
@Getter
@Component
public class JwtProviderImpl implements JwtProvider {

  private final SecretKey secretKey;

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${jwt.access.expiration}")
  private long accessTokenExpire;

  @Value("${jwt.refresh.expiration}")
  private long refreshTokenExpire;

  private static final String PREFIX_BEARER = "Bearer ";

  public JwtProviderImpl(@Value("${jwt.secret}") String base64Secret) {
    byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }


  @Override
  public String createAccessToken(User user) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(user.getId().toString())
        .claim("USER_ROLE", user.getRole())
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + accessTokenExpire))
        .signWith(secretKey)
        .compact();
  }

  @Override
  public String createRefreshToken(User user) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(user.getId().toString())
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
    String subject = extractClaims(bearerToken).getSubject();
    if (subject == null) {
      log.warn("JWT 에 subject 정보가 없습니다.");
      throw new CustomException(AuthErrorCode.INVALID_BEARER_TOKEN);
    }

    return Long.parseLong(subject);
  }

  @Override
  public String getRole(String bearerToken) {
    String roleByToken = extractClaims(bearerToken).get("USER_ROLE", String.class);
    if (roleByToken == null) {
      log.warn("JWT 에 role 정보가 없습니다.");
      throw new CustomException(AuthErrorCode.INVALID_BEARER_TOKEN);
    }

    return roleByToken;
  }

  @Override
  public Long getUserIdByToken(String bearerToken) {
    Claims claims = extractClaims(bearerToken);

    return Long.parseLong(claims.getSubject());
  }


  private Claims extractClaims(String token) {
    if (token.isBlank()) {
      throw new CustomException(INVALID_BEARER_TOKEN);
    }

    String pureToken = token.regionMatches(true, 0, PREFIX_BEARER, 0, PREFIX_BEARER.length())
        ? token.substring(PREFIX_BEARER.length()).trim()
        : token.trim();

    log.info("pureToken: {}", pureToken);

    try {
      return Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(pureToken)
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
