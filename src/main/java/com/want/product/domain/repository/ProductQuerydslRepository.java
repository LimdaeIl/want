package com.want.product.domain.repository;

import com.want.product.application.product.dto.request.ProductSearchCondition;
import com.want.product.application.product.dto.response.FlatProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQuerydslRepository {

  Page<FlatProductDto> findProductsByCondition(ProductSearchCondition condition, Pageable pageable);
}
