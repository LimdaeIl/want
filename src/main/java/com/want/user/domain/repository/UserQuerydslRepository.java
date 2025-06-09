package com.want.user.domain.repository;

import com.want.user.application.dto.auth.request.UserSearchCondition;
import com.want.user.application.dto.auth.response.GetUsersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQuerydslRepository {
  Page<GetUsersResponse> findUsersByCondition(UserSearchCondition condition, Pageable pageable);
}
