package com.gagaswin.silentmeeting.config.filter;

import com.gagaswin.silentmeeting.common.utils.JwtUtil;
import com.gagaswin.silentmeeting.modules.users.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String token = null;
    String userId = null;
    String username = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      userId = jwtUtil.extractSubJwt(token);
      username = jwtUtil.extractClaimJwt(token);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails loadUsername = userService.loadUserByUsername(username);
      if (jwtUtil.verifyJwt(token, userId, username)) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loadUsername, null, loadUsername.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
