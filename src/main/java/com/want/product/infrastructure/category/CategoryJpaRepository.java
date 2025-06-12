package com.want.product.infrastructure.category;

import com.want.product.domain.entity.category.Category;
import com.want.product.domain.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryJpaRepository extends JpaRepository<Category, UUID>, CategoryRepository {

  boolean existsCategoryByName(String name);

  Optional<Category> findById(UUID id);

  @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
  List<Category> findAllByParentIdIsNullFetchChildren();

  @Query("SELECT c.name FROM Category c WHERE c.name IN :names")
  List<String> findNamesIn(@Param("names") Set<String> names);

  @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL")
  List<Category> findAllNotDeleted();
}
