package com.want.user.application.service.user;

import com.want.common.exception.CustomException;
import com.want.user.application.dto.auth.response.GetUserResponse;
import com.want.user.domain.repository.UserRepository;
import com.want.user.domain.user.User;
import com.want.user.domain.user.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private User findUserById(Long id) {
    return userRepository.findUserById(id)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND_BY_ID));
  }

  @Transactional(readOnly = true)
  @Override
  public GetUserResponse getUser(Long id) {
    User userById = findUserById(id);

    return GetUserResponse.from(userById);
  }
}
