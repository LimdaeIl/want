package com.want.user.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
  ROLE_ADMIN("관리자"),
  ROLE_MANAGER("매니저"),
  ROLE_OWNER("가게주인"),
  ROLE_CUSTOMER("구매자");

  private final String name;
}
