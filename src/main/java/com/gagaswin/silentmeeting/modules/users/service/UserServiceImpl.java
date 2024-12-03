package com.gagaswin.silentmeeting.modules.users.service;

import com.gagaswin.silentmeeting.modules.users.model.entity.AppUser;
import com.gagaswin.silentmeeting.modules.users.model.entity.User;
import com.gagaswin.silentmeeting.modules.users.model.entity.UserDetail;
import com.gagaswin.silentmeeting.modules.users.model.response.GetUserResponseDto;
import com.gagaswin.silentmeeting.modules.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return AppUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .build();
  }

  @Override
  public GetUserResponseDto getUserById(String id) throws UsernameNotFoundException {
    User user = userRepository.findByIdWithDetail(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    UserDetail detail = user.getUserDetail();

    return GetUserResponseDto.builder()
        .username(user.getUsername())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .phone(detail.getPhone())
        .dateOfBirth(detail.getDateOfBirth())
        .address(detail.getAddress())
        .company(detail.getCompany())
        .lastEducation(detail.getLastEducation())
        .lastInstitutionName(detail.getLastInstitutionName())
        .build();
  }
}
