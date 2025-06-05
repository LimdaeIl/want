package com.want.common.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.want.common.exception.ErrorResponse;
import com.want.common.infrastructure.jwt.JwtProvider;
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
  private static final ObjectMapper mapper = new ObjectMapper();


  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  @NotNull HttpServletResponse response,
                                  @NotNull FilterChain filterChain) throws ServletException, IOException {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      Long userId = jwtProvider.getUserId(bearerToken);
      String email = jwtProvider.getEmail(bearerToken);
      String role = jwtProvider.getRole(bearerToken);

      List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
      CustomUserDetails userDetails = new CustomUserDetails(userId, email, Role.valueOf(role));

      Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, bearerToken, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);

    } catch (Exception e) {
      log.warn("JWT 인증 실패: {}", e.getMessage());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.getWriter().write(
          mapper.writeValueAsString(new ErrorResponse(AuthErrorCode.INVALID_BEARER_TOKEN))
      );
      SecurityContextHolder.clearContext();

    }
  }
}