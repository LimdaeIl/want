package com.want.order.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.want.common.querydsl.QuerydslSortUtil;
import com.want.common.querydsl.QuerydslWhereUtil;
import com.want.order.application.dto.request.OrderSearchCondition;
import com.want.order.application.dto.response.GetOrdersResponse;
import com.want.order.domain.entity.QOrder;
import com.want.order.domain.repository.OrderQuerydslRepository;
import com.want.user.domain.user.QUser;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQuerydslRepositoryImpl implements OrderQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<GetOrdersResponse> findOrdersByCondition(OrderSearchCondition condition, Pageable pageable) {
    QOrder order = QOrder.order;
    QUser user = QUser.user;

    BooleanBuilder where = new BooleanBuilder()
        .and(QuerydslWhereUtil.eqIfNotNull(order.id, condition.id()))
        .and(QuerydslWhereUtil.eqIfNotNull(order.user.id, condition.userId()))
        .and(QuerydslWhereUtil.eqIfNotNull(order.status, condition.status()))
        .and(QuerydslWhereUtil.like(order.message, condition.message()))
        .and(QuerydslWhereUtil.betweenIfNotNull(order.createdAt, condition.createdFrom(), condition.createdTo()))
        .and(QuerydslWhereUtil.eqIfNotNull(order.createdBy, condition.createdBy()));

    List<OrderSpecifier<?>> orderSpecifiers = QuerydslSortUtil.toOrderSpecifier(
        pageable.getSort(),
        order.getType(),                    // 정확히 Order.class 반환
        order.getMetadata().getName(),      // "order"
        Set.of("id", "createdAt", "status") // 허용된 정렬 필드
    );

    List<GetOrdersResponse> content = jpaQueryFactory
        .select(Projections.constructor(
            GetOrdersResponse.class,
            order.id,
            user.id,
            user.name,
            order.status,
            order.message,
            order.createdAt,
            order.createdBy
        ))
        .from(order)
        .join(order.user, user)
        .where(where)
        .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = jpaQueryFactory
        .select(order.count())
        .from(order)
        .where(where)
        .fetchOne();

    return new PageImpl<>(content, pageable, total != null ? total : 0);
  }
}