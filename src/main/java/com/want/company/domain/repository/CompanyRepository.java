package com.want.company.domain.repository;

import com.want.company.domain.entity.Company;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
  Optional<Company> findCompanyByName(String name);

  Optional<Company> findCompanyById(UUID id);

  Company save(Company company);

  boolean existsCompanyByName(String name);

}
