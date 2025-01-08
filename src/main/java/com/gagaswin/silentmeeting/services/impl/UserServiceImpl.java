package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.models.entity.UserDetail;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import com.gagaswin.silentmeeting.repository.UserRepository;
import com.gagaswin.silentmeeting.services.UserDetailService;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserDetailService userDetailService;

  @Override
  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public User getUserAuth(Authentication authentication) {
    return userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new ResourceNotFoundException("User", "Username", authentication.getName()));
  }

  @Override
  public UserResponseDto getProfile(Authentication authentication) {
    User currentUser = this.getUserAuth(authentication);

    UserDetail userDetail = currentUser.getUserDetail();

    return UserResponseDto.builder()
        .username(currentUser.getUsername())
        .firstName(userDetail.getFirstName())
        .lastName(userDetail.getLastName())
        .email(userDetail.getEmail())
        .phone(userDetail.getPhone())
        .dateOfBirth(userDetail.getDateOfBirth())
        .address(userDetail.getAddress())
        .company(userDetail.getCompany())
        .lastEducation(userDetail.getLastEducation())
        .lastInstitutionName(userDetail.getLastInstitutionName())
        .createdAt(currentUser.getCreatedAt())
        .build();
  }

  private String[] getNullPropertyName(Object source) {
    return Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass()))
        .map(FeatureDescriptor::getName)
        .filter(name -> {
          try {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(source.getClass(), name);
            if (propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
              Object value = propertyDescriptor.getReadMethod().invoke(source);
              return value == null;
            }
          } catch (Exception ignored) {
          }
          return false;
        })
        .toArray(String[]::new);
  }

  @Override
  public UpdateUserProfileResponseDto updateProfile(Authentication authentication,
                                                    UpdateUserProfileRequestDto updateUserProfileRequestDto) {
    User currentUser = this.getUserAuth(authentication);
    UserDetail userDetail = currentUser.getUserDetail();

    BeanUtils.copyProperties(updateUserProfileRequestDto, userDetail, getNullPropertyName(updateUserProfileRequestDto));
    UserDetail updatedUserDetail = userDetailService.save(userDetail);

    LocalDateTime currentUpdate = LocalDateTime.now();
    currentUser.setUpdatedAt(LocalDateTime.now());
    userRepository.save(currentUser);

    return UpdateUserProfileResponseDto.builder()
        .firstName(updatedUserDetail.getFirstName())
        .lastName(updatedUserDetail.getLastName())
        .phone(updatedUserDetail.getPhone())
        .dateOfBirth(updatedUserDetail.getDateOfBirth())
        .address(updatedUserDetail.getAddress())
        .company(updatedUserDetail.getCompany())
        .lastEducation(updatedUserDetail.getLastEducation())
        .lastInstitutionName(updatedUserDetail.getLastInstitutionName())
        .updatedAt(currentUpdate)
        .build();
  }
}
