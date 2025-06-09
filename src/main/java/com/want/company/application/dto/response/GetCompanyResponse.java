package com.want.company.application.dto.response;

import com.want.company.domain.entity.Company;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetCompanyResponse(
    UUID id,
    String name
) {
  public static GetCompanyResponse from(Company companyById) {
    return GetCompanyResponse.builder()
        .id(companyById.getId())
        .name(companyById.getName())
        .build();
  }
}
