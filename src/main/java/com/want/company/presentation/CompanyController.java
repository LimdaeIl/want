package com.want.company.presentation;

import com.want.common.config.PagedResponse;
import com.want.common.response.ApiResponse;
import com.want.company.application.dto.request.CompanySearchCondition;
import com.want.company.application.dto.request.CreateCompanyRequest;
import com.want.company.application.dto.response.CreateCompanyResponse;
import com.want.company.application.dto.response.GetCompaniesResponse;
import com.want.company.application.dto.response.GetCompanyResponse;
import com.want.company.application.service.CompanyService;
import com.want.company.domain.entity.CompanySuccessCode;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController {

  private final CompanyService companyService;

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @PostMapping
  public ResponseEntity<ApiResponse<CreateCompanyResponse>> createCompany(
      @RequestBody @Valid CreateCompanyRequest request
  ) {
    CreateCompanyResponse response = companyService.createCompany(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ApiResponse<>(
                CompanySuccessCode.COMPANY_CREATE_SUCCESS.getCode(),
                CompanySuccessCode.COMPANY_CREATE_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER', 'CUSTOMER')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GetCompanyResponse>> getCompany(
      @PathVariable UUID id
  ) {
    GetCompanyResponse response = companyService.getCompany(id);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                CompanySuccessCode.COMPANIES_GET_SUCCESS.getCode(),
                CompanySuccessCode.COMPANIES_GET_SUCCESS.getMessage(),
                response
            )
        );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'OWNER')")
  @GetMapping
  public ResponseEntity<ApiResponse<PagedResponse<GetCompaniesResponse>>> getCompanies(
      @ParameterObject CompanySearchCondition condition,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Direction.DESC),
          @SortDefault(sort = "name", direction = Direction.DESC)
      })
      Pageable page
  ) {
    PagedResponse<GetCompaniesResponse> response = companyService.getCompanies(condition, page);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse<>(
                CompanySuccessCode.COMPANIES_GET_SUCCESS.getCode(),
                CompanySuccessCode.COMPANIES_GET_SUCCESS.getMessage(),
                response
            )
        );
  }
}
