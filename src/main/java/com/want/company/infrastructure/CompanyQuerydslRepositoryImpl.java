package com.want.company.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.want.common.querydsl.QuerydslSortUtil;
import com.want.common.querydsl.QuerydslWhereUtil;
import com.want.company.application.dto.request.CompanySearchCondition;
import com.want.company.application.dto.response.GetCompaniesResponse;
import com.want.company.domain.entity.QCompany;
import com.want.company.domain.repository.CompanyQuerydslRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CompanyQuerydslRepositoryImpl implements CompanyQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<GetCompaniesResponse> findCompanyByCondition(CompanySearchCondition condition, Pageable pageable) {
    QCompany company = QCompany.company;

    BooleanBuilder where = new BooleanBuilder()
        .and(QuerydslWhereUtil.like(company.name, condition.name()))
        .and(QuerydslWhereUtil.betweenIfNotNull(company.createdAt, condition.createdFrom(), condition.createdTo()))
        .and(QuerydslWhereUtil.betweenIfNotNull(company.updatedAt, condition.updatedFrom(), condition.updatedTo()))
        .and(QuerydslWhereUtil.betweenIfNotNull(company.deletedAt, condition.deletedFrom(), condition.deletedTo()))
        .and(QuerydslWhereUtil.eqIfNotNull(company.createdBy, condition.createdBy()))
        .and(QuerydslWhereUtil.eqIfNotNull(company.updatedBy, condition.updatedBy()))
        .and(QuerydslWhereUtil.eqIfNotNull(company.deletedBy, condition.deletedBy()));

    List<OrderSpecifier<?>> orderSpecifiers = QuerydslSortUtil.toOrderSpecifier(
        pageable.getSort(),
        company.getClass(),
        "company",
        Set.of(
            "id",
            "name",
            "created",
            "createdAt",
            "createdBy",
            "updatedAt",
            "updatedBy",
            "deletedAt",
            "deletedBy"
        )
    );

    List<GetCompaniesResponse> companies = jpaQueryFactory
        .select(Projections.constructor(
            GetCompaniesResponse.class,
            company.id,
            company.name,
            company.createdAt,
            company.createdBy,
            company.updatedAt,
            company.updatedBy,
            company.deletedAt,
            company.deletedBy
        )).from(company)
        .where(where)
        .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = jpaQueryFactory
        .select(company.count())
        .from(company)
        .where(where)
        .fetchOne();

    return new PageImpl<>(companies, pageable, total != null ? total : 0);
  }
}