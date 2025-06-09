package com.want.common.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuerydslSortUtil {

  public static <T> List<OrderSpecifier<?>> toOrderSpecifier(
      Sort sort,
      Class<T> clazz,
      String alias,
      Set<String> allowedFields
  ) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    PathBuilder<T> entityPath = new PathBuilder<>(clazz, alias);

    for (Sort.Order order : sort) {
      String property = order.getProperty().trim();
      if (!allowedFields.contains(property)) {
        continue;
      }

      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      orderSpecifiers.add(new OrderSpecifier<>(
          direction,
          entityPath.getComparable(property, Comparable.class)
      ));
    }
    return orderSpecifiers;
  }
}
