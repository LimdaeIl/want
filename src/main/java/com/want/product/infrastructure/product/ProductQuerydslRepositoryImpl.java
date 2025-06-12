package com.want.product.infrastructure.product;

import static com.want.common.querydsl.QuerydslWhereUtil.betweenIfNotNull;
import static com.want.common.querydsl.QuerydslWhereUtil.eqIfNotNull;
import static com.want.common.querydsl.QuerydslWhereUtil.like;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.want.common.querydsl.QuerydslSortUtil;
import com.want.product.application.product.dto.request.ProductSearchCondition;
import com.want.product.application.product.dto.response.FlatProductDto;
import com.want.product.domain.entity.product.QProduct;
import com.want.product.domain.repository.ProductQuerydslRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<FlatProductDto> findProductsByCondition(ProductSearchCondition condition, Pageable pageable) {
    QProduct product = QProduct.product;

    BooleanBuilder where = new BooleanBuilder()
        .and(eqIfNotNull(product.id, condition.id()))
        .and(like(product.name, condition.name()))
        .and(betweenIfNotNull(product.price, condition.minPrice(), condition.maxPrice()))
        .and(betweenIfNotNull(product.quantity, condition.minQuantity(), condition.maxQuantity()))
        .and(eqIfNotNull(product.saleStatus, condition.saleStatus()))
        .and(eqIfNotNull(product.thumbnail, condition.thumbnail()))
        .and(like(product.description, condition.description()))
        .and(eqIfNotNull(product.category.id, condition.categoryId()))
        .and(eqIfNotNull(product.company.id, condition.companyId()))
        .and(eqIfNotNull(product.productPolicy.id, condition.productPolicyId()))
        .and(betweenIfNotNull(product.createdAt, condition.createdFrom(), condition.createdTo()))
        .and(betweenIfNotNull(product.updatedAt, condition.updatedFrom(), condition.updatedTo()))
        .and(betweenIfNotNull(product.deletedAt, condition.deletedFrom(), condition.deletedTo()))
        .and(condition.includeDeleted() != null && !condition.includeDeleted() ? product.deletedAt.isNull() : null)
        .and(eqIfNotNull(product.productPolicy.discountType, condition.discountType()))
        .and(eqIfNotNull(product.productPolicy.isActive, condition.isPolicyActive()))
        .and(betweenIfNotNull(product.productPolicy.value, condition.minPolicyValue(), condition.maxPolicyValue()))
        .and(betweenIfNotNull(product.productPolicy.startedAt,
            condition.policyStartedFrom(),
            condition.policyStartedTo()))
        .and(betweenIfNotNull(product.productPolicy.endedAt, condition.policyEndedFrom(), condition.policyEndedTo()))
        .and(condition.minPurchaseAmount() != null
            ? product.productPolicy.minPurchaseAmount.goe(condition.minPurchaseAmount())
            : null);

    List<OrderSpecifier<?>> orderSpecifiers = QuerydslSortUtil.toOrderSpecifier(
        pageable.getSort(),
        product.getClass(),
        "product",
        Set.of("id",
            "name",
            "price",
            "quantity",
            "saleStatus",
            "createdAt",
            "createdBy",
            "updatedAt",
            "updatedBy",
            "deletedAt",
            "deletedBy")
    );

    List<FlatProductDto> flats = jpaQueryFactory
        .select(Projections.constructor(
            FlatProductDto.class,
            product.id,
            product.name,
            product.price,
            product.quantity,
            product.thumbnail,
            product.saleStatus,
            product.description,

            product.category.id,
            product.category.name,
            product.company.id,
            product.company.name,
            product.productPolicy.id,
            product.productPolicy.name,
            product.productPolicy.description,
            product.productPolicy.discountType,
            product.productPolicy.value,
            product.productPolicy.startedAt,
            product.productPolicy.endedAt,
            product.productPolicy.isActive,
            product.productPolicy.minPurchaseAmount,

            product.createdAt,
            product.createdBy,
            product.updatedAt,
            product.updatedBy,
            product.deletedAt,
            product.deletedBy
        ))
        .from(product)
        .leftJoin(product.category)
        .leftJoin(product.company)
        .leftJoin(product.productPolicy)
        .where(where)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
        .fetch();

    Long total = jpaQueryFactory
        .select(product.count())
        .from(product)
        .leftJoin(product.category)
        .leftJoin(product.company)
        .leftJoin(product.productPolicy)
        .where(where)
        .fetchOne();

    return new PageImpl<>(flats, pageable, total != null ? total : 0);
  }

}