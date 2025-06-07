package com.want.common.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.want.common.exception.ErrorResponse;
import com.want.common.infrastructure.jwt.JwtProvider;
import com.want.common.infrastructure.redis.RedisService;
import com.want.user.domain.auth.AuthErrorCode;
import com.want.user.domain.user.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Slf4j(topic = "JwtTokenFilter")
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final RedisService redisService;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  @NotNull HttpServletResponse response,
                                  @NotNull FilterChain filterChain) throws ServletException, IOException {

    String bearer = request.getHeader("Authorization");

    if (bearer == null || !bearer.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = bearer.substring(7);

    try {
      if (isTokenBlacklisted(token)) {
        log.warn("차단된 토큰으로 접근 시도");
        throw new SecurityException("블랙리스트 토큰입니다.");
      }

      Long userId = jwtProvider.getUserId(token);
      String roleString = jwtProvider.getRole(token);

      Role role = Role.valueOf(roleString);
      List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));

      CustomUserDetails userDetails = new CustomUserDetails(userId, role);

      Authentication authentication =
          new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);

    } catch (Exception e) {
      log.warn("JWT 인증 실패: {}", e.getMessage());

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.getWriter().write(
          objectMapper.writeValueAsString(new ErrorResponse(AuthErrorCode.INVALID_BEARER_TOKEN))
      );
      SecurityContextHolder.clearContext();
    }
  }

  private boolean isTokenBlacklisted(String token) {
    try {
      Long userId = jwtProvider.getUserId(token);
      String redisKey = "BL:AT:" + userId;

      return redisService.get(redisKey)
          .map(blacklistedToken -> blacklistedToken.equals(token))
          .orElse(false);

    } catch (Exception e) {
      log.warn("블랙리스트 조회 중 예외 발생: {}", e.getMessage());
      return false;
    }
  }
}
