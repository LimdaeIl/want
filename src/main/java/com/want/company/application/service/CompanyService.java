package com.want.company.application.service;

import com.want.common.config.PagedResponse;
import com.want.company.application.dto.request.CompanySearchCondition;
import com.want.company.application.dto.request.CreateCompanyRequest;
import com.want.company.application.dto.response.CreateCompanyResponse;
import com.want.company.application.dto.response.GetCompaniesResponse;
import com.want.company.application.dto.response.GetCompanyResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
  CreateCompanyResponse createCompany(CreateCompanyRequest request);

  GetCompanyResponse getCompany(UUID id);

  PagedResponse<GetCompaniesResponse> getCompanies(CompanySearchCondition condition, Pageable page);
}
