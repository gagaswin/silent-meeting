package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.models.entity.AppUser;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.models.entity.UserDetail;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import com.gagaswin.silentmeeting.repository.UserDetailRepository;
import com.gagaswin.silentmeeting.repository.UserRepository;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserDetailRepository userDetailRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return AppUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .build();
  }

  @Override
  public User currentUserAuth(Authentication authentication) {
    return userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Override
  public UserResponseDto getProfile(Authentication authentication) {
    User currentUser = currentUserAuth(authentication);

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
    User currentUser = currentUserAuth(authentication);

    UserDetail userDetail = currentUser.getUserDetail();

    BeanUtils.copyProperties(updateUserProfileRequestDto, userDetail, getNullPropertyName(updateUserProfileRequestDto));
    UserDetail updatedUserDetail = userDetailRepository.saveAndFlush(userDetail);

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
