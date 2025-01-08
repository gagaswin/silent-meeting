package com.gagaswin.silentmeeting.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.services.CustomUserDetailsService;
import com.gagaswin.silentmeeting.utils.JwtUtil;
import com.gagaswin.silentmeeting.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);

        String userId = jwtUtil.extractSubJwt(token);
        String username = jwtUtil.extractClaimJwt(token);

        jwtUtil.verifyJwt(token, userId, username);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

      filterChain.doFilter(request, response);
    } catch (TokenExpiredException e) {
      filterErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    } catch (JWTVerificationException e) {
      filterErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
  }

  private void filterErrorResponse(HttpServletResponse servletResponse, int httpStatus, String message) throws IOException {
    CommonResponseDto<?> errorResponse = ResponseUtil.createResponse(httpStatus, message);

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponse = objectMapper.writeValueAsString(errorResponse);

    servletResponse.setStatus(httpStatus);
    servletResponse.setContentType("application/json");
    servletResponse.setCharacterEncoding("UTF-8");
    servletResponse.getWriter().write(jsonResponse);
  }
}
