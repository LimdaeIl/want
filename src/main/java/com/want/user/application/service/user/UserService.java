package com.want.user.application.service.user;

import com.want.common.config.PagedResponse;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.user.application.dto.auth.request.UserSearchCondition;
import com.want.user.application.dto.auth.response.GetMeResponse;
import com.want.user.application.dto.auth.response.GetUserResponse;
import com.want.user.application.dto.auth.response.GetUsersResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {


  GetUserResponse getUser(Long id);

  GetMeResponse getMe(CustomUserDetails userDetails);

  PagedResponse<GetUsersResponse> getUsers(UserSearchCondition condition, Pageable page);
}
