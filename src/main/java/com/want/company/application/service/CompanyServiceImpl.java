package com.want.company.application.service;

import com.want.common.config.PagedResponse;
import com.want.common.exception.CustomException;
import com.want.company.application.dto.request.CompanySearchCondition;
import com.want.company.application.dto.request.CreateCompanyRequest;
import com.want.company.application.dto.response.CreateCompanyResponse;
import com.want.company.application.dto.response.GetCompaniesResponse;
import com.want.company.application.dto.response.GetCompanyResponse;
import com.want.company.domain.entity.Company;
import com.want.company.domain.entity.CompanyErrorCode;
import com.want.company.domain.repository.CompanyQuerydslRepository;
import com.want.company.domain.repository.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final CompanyQuerydslRepository companyQuerydslRepository;

  private Company findCompanyByName(String name) {
    return companyRepository.findCompanyByName(name)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));
  }

  private Company findCompanyById(UUID id) {
    return companyRepository.findById(id)
        .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));
  }

  private void existsCompanyByName(String name) {
    if (companyRepository.existsCompanyByName(name)) {
      throw new CustomException(CompanyErrorCode.COMPANY_NAME_DUPLICATE);
    }
  }

  @Transactional
  @Override
  public CreateCompanyResponse createCompany(CreateCompanyRequest request) {
    existsCompanyByName(request.name());

    Company buildCompany = Company.builder()
        .name(request.name())
        .build();

    Company savedCompany = companyRepository.save(buildCompany);

    return CreateCompanyResponse.from(savedCompany);
  }

  @Transactional(readOnly = true)
  @Override
  public GetCompanyResponse getCompany(UUID id) {
    Company companyById = findCompanyById(id);

    return GetCompanyResponse.from(companyById);
  }

  @Transactional(readOnly = true)
  @Override
  public PagedResponse<GetCompaniesResponse> getCompanies(CompanySearchCondition condition, Pageable page) {
    Page<GetCompaniesResponse> companyByCondition = companyQuerydslRepository.findCompanyByCondition(condition, page);

    return PagedResponse.from(companyByCondition);
  }
}
