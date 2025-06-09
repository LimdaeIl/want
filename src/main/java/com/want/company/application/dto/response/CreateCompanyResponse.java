package com.want.company.application.dto.response;

import com.want.company.domain.entity.Company;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateCompanyResponse(
    UUID id,
    String name
) {
  public static CreateCompanyResponse from(Company buildCompany) {
    return CreateCompanyResponse.builder()
        .id(buildCompany.getId())
        .name(buildCompany.getName())
        .build();
  }
}
