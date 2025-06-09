package com.want.user.application.service.user;

import com.want.common.config.PagedResponse;
import com.want.common.exception.CustomException;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.user.application.dto.auth.request.UserSearchCondition;
import com.want.user.application.dto.user.request.UpdateEmailRequest;
import com.want.user.application.dto.user.request.UpdateInfoRequest;
import com.want.user.application.dto.user.request.UpdatePasswordRequest;
import com.want.user.application.dto.user.request.UpdatePhoneRequest;
import com.want.user.application.dto.user.request.UpdateRoleRequest;
import com.want.user.application.dto.user.response.GetMeResponse;
import com.want.user.application.dto.user.response.GetUserResponse;
import com.want.user.application.dto.user.response.GetUsersResponse;
import com.want.user.application.dto.user.response.UpdateEmailResponse;
import com.want.user.application.dto.user.response.UpdateInfoResponse;
import com.want.user.application.dto.user.response.UpdatePhoneResponse;
import com.want.user.application.dto.user.response.UpdateRoleResponse;
import com.want.user.domain.repository.UserQuerydslRepository;
import com.want.user.domain.repository.UserRepository;
import com.want.user.domain.user.Role;
import com.want.user.domain.user.User;
import com.want.user.domain.user.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserQuerydslRepository userQuerydslRepository;
  private final PasswordEncoder passwordEncoder;

  private User findUserById(Long id) {
    return userRepository.findUserById(id).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  @Transactional(readOnly = true)
  @Override
  public GetUserResponse getUser(Long id) {
    User userById = findUserById(id);

    return GetUserResponse.from(userById);
  }

  @Transactional(readOnly = true)
  @Override
  public GetMeResponse getMe(CustomUserDetails userDetails) {
    User userById = findUserById(userDetails.id());

    return GetMeResponse.from(userById);
  }

  @Transactional(readOnly = true)
  @Override
  public PagedResponse<GetUsersResponse> getUsers(UserSearchCondition condition, Pageable page) {
    Page<GetUsersResponse> usersByCondition = userQuerydslRepository.findUsersByCondition(condition, page);

    return PagedResponse.from(usersByCondition);
  }

  @Transactional
  @Override
  public UpdateEmailResponse updateEmail(CustomUserDetails userDetails, Long id, UpdateEmailRequest request) {
    if (!userDetails.id().equals(id) && !userDetails.role().equals(Role.ROLE_ADMIN)) {
      throw new CustomException(UserErrorCode.USER_EMAIL_UPDATE_FORBIDDEN);
    }

    User userById = findUserById(id);
    userById.updateEmail(request.newEmail());

    return UpdateEmailResponse.from(userById);
  }

  @Transactional
  @Override
  public void updatePassword(CustomUserDetails userDetails, Long id, UpdatePasswordRequest request) {
    if (!userDetails.id().equals(id) && !userDetails.role().equals(Role.ROLE_ADMIN)) {
      throw new CustomException(UserErrorCode.USER_PASSWORD_UPDATE_FORBIDDEN);
    }

    User userById = findUserById(id);

    if (!passwordEncoder.matches(request.password(), userById.getPassword())) {
      throw new CustomException(UserErrorCode.USER_PASSWORD_INCORRECT);
    }

    if (request.password().equals(request.newPassword())) {
      throw new CustomException(UserErrorCode.USER_PASSWORD_SAME_AS_OLD);
    }

    userById.updatePassword(passwordEncoder.encode(request.newPassword()));
  }

  @Transactional
  @Override
  public UpdateInfoResponse updateInfo(CustomUserDetails userDetails, Long id, UpdateInfoRequest request) {
    if (!userDetails.id().equals(id) && !userDetails.role().equals(Role.ROLE_ADMIN)) {
      throw new CustomException(UserErrorCode.USER_INFO_UPDATE_FORBIDDEN);
    }

    User userById = findUserById(id);

    userById.updateName(request.newName());
    userById.updateProfileImage(request.newProfileImage());

    return UpdateInfoResponse.from(userById);
  }

  @Transactional
  @Override
  public UpdateRoleResponse updateRole(Long id, UpdateRoleRequest request) {
    User userById = findUserById(id);

    userById.updateRole(request.newRole());

    return UpdateRoleResponse.from(userById);
  }

  @Transactional
  @Override
  public UpdatePhoneResponse updatePhone(CustomUserDetails userDetails, Long id, UpdatePhoneRequest request) {
    if (!userDetails.id().equals(id) && !userDetails.role().equals(Role.ROLE_ADMIN)) {
      throw new CustomException(UserErrorCode.USER_INFO_UPDATE_FORBIDDEN);
    }

    User userById = findUserById(id);

    if (userById.getPhone().equals(request.newPhone())) {
      throw new CustomException(UserErrorCode.USER_PHONE_SAME_AS_OLD);
    }
    userById.updatePhone(request.newPhone());

    return UpdatePhoneResponse.from(userById);
  }
}
