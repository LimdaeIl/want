package com.want.product.domain.entity.category;

import com.want.common.audit.BaseEntity;
import com.want.common.exception.CustomException;
import com.want.product.domain.entity.product.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "p_categories")
@Entity
public class Category extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @Builder.Default
  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
  private List<Category> children = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<ProductCategory> productCategories = new ArrayList<>();

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  public void changeName(String newName) {
    if (newName == null || newName.isBlank()) {
      throw new CustomException(CategoryErrorCode.CATEGORY_NAME_BLACK);
    }
    this.name = newName;
  }

  public void changeParent(Category newParent) {
    this.parent = newParent;
  }
}
