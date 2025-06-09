package com.want.company.infrastructure;

import com.want.company.domain.entity.Company;
import com.want.company.domain.repository.CompanyRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, UUID>, CompanyRepository {


}
