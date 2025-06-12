package com.want.product.domain.repository;

import com.want.product.domain.entity.category.Category;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository {

  boolean existsCategoryByName(String name);

  Optional<Category> findById(UUID id);

  Optional<Category> findCategoryByName(String name);

  Category save(Category category);

  List<Category> findAll();

  List<Category> findAllByParentIdIsNullFetchChildren();

  List<String> findNamesIn(Set<String> allNames);

  List<Category> findAllNotDeleted();
}
