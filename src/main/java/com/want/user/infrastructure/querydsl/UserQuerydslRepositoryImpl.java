package com.want.user.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.want.common.querydsl.QuerydslSortUtil;
import com.want.common.querydsl.QuerydslWhereUtil;
import com.want.user.application.dto.auth.request.UserSearchCondition;
import com.want.user.application.dto.auth.response.GetUsersResponse;
import com.want.user.domain.repository.UserQuerydslRepository;
import com.want.user.domain.user.QUser;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQuerydslRepositoryImpl implements UserQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<GetUsersResponse> findUsersByCondition(UserSearchCondition condition, Pageable pageable) {
    QUser user = QUser.user;

    BooleanBuilder where = new BooleanBuilder()
        .and(QuerydslWhereUtil.eqIfNotNull(user.id, condition.id()))
        .and(QuerydslWhereUtil.like(user.email, condition.email()))
        .and(QuerydslWhereUtil.like(user.phone, condition.phone()))
        .and(QuerydslWhereUtil.like(user.name, condition.name()))
        .and(QuerydslWhereUtil.eqIfNotNull(user.role, condition.role()))
        .and(QuerydslWhereUtil.betweenIfNotNull(user.createdAt, condition.createdFrom(), condition.createdTo()))
        .and(QuerydslWhereUtil.betweenIfNotNull(user.updatedAt, condition.updatedFrom(), condition.updatedTo()))
        .and(QuerydslWhereUtil.betweenIfNotNull(user.deletedAt, condition.deletedFrom(), condition.deletedTo()))
        .and(QuerydslWhereUtil.eqIfNotNull(user.createdBy, condition.createdBy()))
        .and(QuerydslWhereUtil.eqIfNotNull(user.updatedBy, condition.updatedBy()))
        .and(QuerydslWhereUtil.eqIfNotNull(user.deletedBy, condition.deletedBy()));

    List<OrderSpecifier<?>> orderSpecifiers = QuerydslSortUtil.toOrderSpecifier(
        pageable.getSort(),
        user.getClass(),
        "user",
        Set.of(
            "id",
            "email",
            "name",
            "profileImage",
            "role",
            "createdAt",
            "createdBy",
            "updatedAt",
            "updatedBy",
            "deletedAt",
            "deletedBy"
        )
    );

    List<GetUsersResponse> users = jpaQueryFactory
        .select(Projections.constructor(
                GetUsersResponse.class,
                user.id,
                user.email,
                user.name,
                user.profileImage,
                user.role,
                user.createdAt,
                user.createdBy,
                user.updatedAt,
                user.updatedBy,
                user.deletedAt,
                user.deletedBy
            )
        ).from(user)
        .where(where)
        .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = jpaQueryFactory
        .select(user.count())
        .from(user)
        .where(where)
        .fetchOne();

    return new PageImpl<>(users, pageable, total != null ? total : 0);
  }
}
