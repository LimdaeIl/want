package com.want.user.application.service.user;

import com.want.common.config.PagedResponse;
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
import org.springframework.data.domain.Pageable;

public interface UserService {


  GetUserResponse getUser(Long id);

  GetMeResponse getMe(CustomUserDetails userDetails);

  PagedResponse<GetUsersResponse> getUsers(UserSearchCondition condition, Pageable page);

  UpdateEmailResponse updateEmail(CustomUserDetails userDetails, Long id, UpdateEmailRequest request);

  void updatePassword(CustomUserDetails userDetails, Long id, UpdatePasswordRequest request);

  UpdateInfoResponse updateInfo(CustomUserDetails userDetails, Long id, UpdateInfoRequest request);

  UpdateRoleResponse updateRole(Long id, UpdateRoleRequest request);

  UpdatePhoneResponse updatePhone(CustomUserDetails userDetails, Long id, UpdatePhoneRequest request);
}
