package com.want.user.application.service.user;

import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.user.application.dto.auth.response.GetMeResponse;
import com.want.user.application.dto.auth.response.GetUserResponse;

public interface UserService {


  GetUserResponse getUser(Long id);

  GetMeResponse getMe(CustomUserDetails userDetails);
}
