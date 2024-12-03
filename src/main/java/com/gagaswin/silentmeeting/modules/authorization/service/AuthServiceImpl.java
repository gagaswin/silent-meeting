package com.gagaswin.silentmeeting.modules.authorization.service;

import com.gagaswin.silentmeeting.common.exceptions.UserAlreadyExistsException;
import com.gagaswin.silentmeeting.common.utils.JwtUtil;
import com.gagaswin.silentmeeting.modules.authorization.model.entity.AuthJwtRefresh;
import com.gagaswin.silentmeeting.modules.authorization.model.request.LoginRequestDto;
import com.gagaswin.silentmeeting.modules.authorization.model.request.RegisterRequestDto;
import com.gagaswin.silentmeeting.modules.authorization.model.response.AuthResponse;
import com.gagaswin.silentmeeting.modules.authorization.repository.AuthJwtRefreshRepository;
import com.gagaswin.silentmeeting.modules.users.model.entity.AppUser;
import com.gagaswin.silentmeeting.modules.users.model.entity.User;
import com.gagaswin.silentmeeting.modules.users.model.entity.UserDetail;
import com.gagaswin.silentmeeting.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final AuthJwtRefreshRepository authJwtRefreshRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Override
  public AuthResponse register(RegisterRequestDto registerRequestDto) {
    Optional<User> existingByUsername = userRepository.findByUsername(registerRequestDto.getUsername());
    if (existingByUsername.isPresent()) {
      throw new UserAlreadyExistsException("Username already exist");
    }

    Optional<User> existingByEmail = userRepository.findByEmail(registerRequestDto.getEmail());
    if (existingByEmail.isPresent()) {
      throw new UserAlreadyExistsException("Email already exist");
    }

    UserDetail userDetail = UserDetail.builder()
        .phone(registerRequestDto.getPhone())
        .dateOfBirth(registerRequestDto.getDateOfBirth())
        .address(registerRequestDto.getAddress())
        .company(registerRequestDto.getCompany())
        .lastEducation(registerRequestDto.getLastEducation())
        .lastInstitutionName(registerRequestDto.getLastInstitutionName())
        .build();

    User user = User.builder()
        .username(registerRequestDto.getUsername())
        .firstName(registerRequestDto.getFirstName())
        .lastName(registerRequestDto.getLastName())
        .email(registerRequestDto.getEmail())
        .password(passwordEncoder.encode(registerRequestDto.getPassword()))
        .createdAt(LocalDateTime.now())
        .userDetail(userDetail)
        .build();

    userDetail.setUser(user);

    userRepository.save(user);

    LoginRequestDto loginRequestDto = new LoginRequestDto(
        registerRequestDto.getUsername(),
        registerRequestDto.getPassword()
    );

    return login(loginRequestDto);
  }

  @Override
  public AuthResponse login(LoginRequestDto loginRequestDto) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequestDto.getUsername(),
        loginRequestDto.getPassword()
    ));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    AppUser appUser = (AppUser) authentication.getPrincipal();
    User user = userRepository.findByUsername(appUser.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    String accessToken = jwtUtil.generateToken(appUser);
    String refreshToken = jwtUtil.generateRefreshToken(appUser);

    AuthJwtRefresh authJwtRefresh = AuthJwtRefresh.builder()
        .refreshToken(refreshToken)
        .issuedAt(jwtUtil.extractIssuedAtJwtRefresh(refreshToken))
        .expiresAt(jwtUtil.extractExpiresAtJwtRefresh(refreshToken))
        .user(user)
        .build();
    authJwtRefreshRepository.save(authJwtRefresh);

    return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthResponse refresh(String refreshToken) {
    AuthJwtRefresh authJwtRefresh = authJwtRefreshRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

    if (authJwtRefresh.isRevoked() || authJwtRefresh.getExpiresAt().before(Date.from(Instant.now()))) {
      throw new IllegalArgumentException("Refresh token is expired or revoke");
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

    return AuthResponse.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .build();
  }
}
