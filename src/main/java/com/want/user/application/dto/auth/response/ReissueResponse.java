package com.want.user.application.dto.auth.response;

import lombok.Builder;

@Builder
public record ReissueResponse(
    String accessToken
) {

  public static ReissueResponse from(ReissueResult reissueResult) {
    return ReissueResponse.builder()
        .accessToken(reissueResult.accessToken())
        .build();
  }

}
