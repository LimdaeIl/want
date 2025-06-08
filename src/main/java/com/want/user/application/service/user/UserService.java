package com.want.user.application.service.user;

import com.want.user.application.dto.auth.response.GetUserResponse;

public interface UserService {


  GetUserResponse getUser(Long id);
}
