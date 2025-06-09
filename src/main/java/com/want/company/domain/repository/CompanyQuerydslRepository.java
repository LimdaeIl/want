package com.want.company.domain.repository;

import com.want.company.application.dto.request.CompanySearchCondition;
import com.want.company.application.dto.response.GetCompaniesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyQuerydslRepository {
  Page<GetCompaniesResponse> findCompanyByCondition(CompanySearchCondition condition, Pageable page);
}
