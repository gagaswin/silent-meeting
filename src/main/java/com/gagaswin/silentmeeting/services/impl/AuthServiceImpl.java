package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.InvalidRefreshTokenException;
import com.gagaswin.silentmeeting.exceptions.UserAlreadyExistsException;
import com.gagaswin.silentmeeting.services.AuthService;
import com.gagaswin.silentmeeting.services.UserDetailService;
import com.gagaswin.silentmeeting.services.UserService;
import com.gagaswin.silentmeeting.utils.JwtUtil;
import com.gagaswin.silentmeeting.models.entity.AuthJwtRefresh;
import com.gagaswin.silentmeeting.models.dtos.auth.LoginUserRequestDto;
import com.gagaswin.silentmeeting.models.dtos.auth.RegisterUserRequestDto;
import com.gagaswin.silentmeeting.models.dtos.auth.AuthResponseDto;
import com.gagaswin.silentmeeting.repository.AuthJwtRefreshRepository;
import com.gagaswin.silentmeeting.models.entity.AppUser;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.models.entity.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthJwtRefreshRepository authJwtRefreshRepository;
  private final UserService userService;
  private final UserDetailService userDetailService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Override
  public AuthResponseDto register(RegisterUserRequestDto registerUserRequestDto) {
    if (userService.getByUsername(registerUserRequestDto.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException("Username", registerUserRequestDto.getUsername());
    }

    if (userDetailService.getByEmail(registerUserRequestDto.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("Email", registerUserRequestDto.getEmail());
    }

    UserDetail userDetail = UserDetail.builder()
        .firstName(registerUserRequestDto.getFirstName())
        .lastName(registerUserRequestDto.getLastName())
        .email(registerUserRequestDto.getEmail())
        .phone(registerUserRequestDto.getPhone())
        .dateOfBirth(registerUserRequestDto.getDateOfBirth())
        .address(registerUserRequestDto.getAddress())
        .company(registerUserRequestDto.getCompany())
        .lastEducation(registerUserRequestDto.getLastEducation())
        .lastInstitutionName(registerUserRequestDto.getLastInstitutionName())
        .build();

    User user = User.builder()
        .username(registerUserRequestDto.getUsername())
        .password(passwordEncoder.encode(registerUserRequestDto.getPassword()))
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .userDetail(userDetail)
        .build();

    userDetail.setUser(user);

    userService.save(user);

    LoginUserRequestDto loginUserRequestDto = new LoginUserRequestDto(
        registerUserRequestDto.getUsername(),
        registerUserRequestDto.getPassword()
    );

    return login(loginUserRequestDto);
  }

  @Override
  public AuthResponseDto login(LoginUserRequestDto loginUserRequestDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserRequestDto.getUsername(),
            loginUserRequestDto.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    AppUser appUser = (AppUser) authentication.getPrincipal();

    User user = userService.getByUsername(appUser.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found: org.springframework.security.core.userdetails.UsernameNotFoundException"));

    String accessToken = jwtUtil.generateToken(appUser);
    String refreshToken = jwtUtil.generateRefreshToken(appUser);

    AuthJwtRefresh authJwtRefresh = AuthJwtRefresh.builder()
        .refreshToken(refreshToken)
        .issuedAt(jwtUtil.extractIssuedAtJwtRefresh(refreshToken))
        .expiresAt(jwtUtil.extractExpiresAtJwtRefresh(refreshToken))
        .user(user)
        .build();
    authJwtRefreshRepository.save(authJwtRefresh);

    return AuthResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthResponseDto refresh(String refreshToken) {
    AuthJwtRefresh authJwtRefresh = authJwtRefreshRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

    if (authJwtRefresh.isRevoked() || authJwtRefresh.getExpiresAt().before(Date.from(Instant.now()))) {
      throw new CredentialsExpiredException("Refresh token is expired or revoke");
    }

    User user = authJwtRefresh.getUser();

    AppUser appUser = AppUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .build();

    String newAccessToken = jwtUtil.generateToken(appUser);
    String newRefreshToken = jwtUtil.generateRefreshToken(appUser);

    authJwtRefresh.setRevoked(true);
    authJwtRefreshRepository.save(authJwtRefresh);

    AuthJwtRefresh newAuthJwtRefresh = AuthJwtRefresh.builder()
        .refreshToken(newRefreshToken)
        .issuedAt(jwtUtil.extractIssuedAtJwtRefresh(newRefreshToken))
        .expiresAt(jwtUtil.extractExpiresAtJwtRefresh(newRefreshToken))
        .user(user)
        .build();
    authJwtRefreshRepository.save(newAuthJwtRefresh);

    return AuthResponseDto.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .build();
  }
}
