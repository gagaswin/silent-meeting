package com.gagaswin.silentmeeting.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gagaswin.silentmeeting.modules.users.model.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {
  @Value("${spring.application.name}")
  private String jwtIss;

  @Value("${silent-meeting.configuration.jwt.jwt-secret}")
  private String jwtSecret;

  @Value("${silent-meeting.configuration.jwt.jwt-expires-at}")
  private Integer jwtExpAt;

  @Value("${silent-meeting.configuration.jwt.jwt-refresh}")
  private String jwtRefreshSecret;

  @Value("${silent-meeting.configuration.jwt.jwt-refresh-expires-at}")
  private Integer jwtRefreshExpAt;

  public String generateToken(AppUser appUser) {
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
    return JWT.create()
        .withIssuer(jwtIss)
        .withSubject(appUser.getId())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusSeconds(jwtExpAt))
        .withClaim("username", appUser.getUsername())
        .sign(algorithm);
  }

  public String generateRefreshToken(AppUser appUser) {
    Algorithm algorithm = Algorithm.HMAC256(jwtRefreshSecret.getBytes(StandardCharsets.UTF_8));
    return JWT.create()
        .withIssuer(jwtIss)
        .withSubject(appUser.getId())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusSeconds(jwtRefreshExpAt))
        .withClaim("username", appUser.getUsername())
        .sign(algorithm);
  }

  private DecodedJWT decodedToken(String token, String secretKey) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
    JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtIss).build();
    return verifier.verify(token);
  }

  public String extractSubJwt(String accessToken) {
    return decodedToken(accessToken, jwtSecret).getSubject();
  }

  public String extractSubJwtRefresh(String refreshToken) {
    return decodedToken(refreshToken, jwtRefreshSecret).getSubject();
  }

  public String extractClaimJwt(String accessToken) {
    return decodedToken(accessToken, jwtSecret).getClaim("username").asString();
  }

  public String extractClaimJwtRefresh(String refreshToken) {
    return decodedToken(refreshToken, jwtRefreshSecret).getClaim("username").asString();
  }

  public Date extractIssuedAtJwtRefresh(String refreshToken) {
    return decodedToken(refreshToken, jwtRefreshSecret).getIssuedAt();
  }

  public Date extractExpiresAtJwtRefresh(String refreshToken) {
    return decodedToken(refreshToken, jwtRefreshSecret).getExpiresAt();
  }

  public Boolean verifyJwt(String accessToken, String currentUserId, String currentUsername) throws JWTVerificationException {
    DecodedJWT decodedJWT = decodedToken(accessToken, jwtSecret);

    if (decodedJWT.getExpiresAt().before(new Date())) {
      throw new TokenExpiredException("Token has expired", decodedJWT.getExpiresAt().toInstant());
    }

    String subDecode = decodedJWT.getSubject();
    if (subDecode == null || subDecode.isEmpty()) {
      throw new JWTVerificationException("Token has not subject");
    } else if (!subDecode.equals(currentUserId)) {
      throw new JWTVerificationException("Token subject does not match the current user information");
    }

    String claimDecode = decodedJWT.getClaim("username").asString();
    if (!claimDecode.equals(currentUsername)) {
      throw new JWTVerificationException("Token claim does not match with current user information");
    }

    return true;
  }

  public Boolean verifyJwtRefresh(String refreshToken, String currentUserId, String currentUsername)
      throws JWTVerificationException {
    DecodedJWT decodedJWT = decodedToken(refreshToken, jwtRefreshSecret);

    if (decodedJWT.getExpiresAt().before(new Date())) {
      throw new TokenExpiredException("Refresh token has expired", decodedJWT.getExpiresAt().toInstant());
    }

    String subDecode = decodedJWT.getSubject();
    if (subDecode == null || subDecode.isEmpty()) {
      throw new JWTVerificationException("Refresh token has not subject");
    } else if (!subDecode.equals(currentUserId)) {
      throw new JWTVerificationException("Refresh token subject does not match the current user information");
    }

    String claimDecode = decodedJWT.getClaim("username").asString();
    if (!claimDecode.equals((currentUsername))) {
      throw new JWTVerificationException("Refresh token claim does not match with current user information");
    }

    return true;
  }
}
