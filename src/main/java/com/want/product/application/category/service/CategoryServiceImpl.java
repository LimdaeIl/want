package com.want.product.application.category.service;

import com.want.common.exception.CustomException;
import com.want.common.infrastructure.security.CustomUserDetails;
import com.want.product.application.category.dto.request.CreateCategoriesRequest;
import com.want.product.application.category.dto.request.CreateCategoryRequest;
import com.want.product.application.category.dto.request.GetCategoryRequest;
import com.want.product.application.category.dto.request.UpdateCategoryRequest;
import com.want.product.application.category.dto.response.CreateCategoriesResponse;
import com.want.product.application.category.dto.response.CreateCategoryResponse;
import com.want.product.application.category.dto.response.DeleteCategoryResponse;
import com.want.product.application.category.dto.response.GetCategoryResponse;
import com.want.product.domain.entity.category.Category;
import com.want.product.domain.entity.category.CategoryErrorCode;
import com.want.product.domain.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private void existsCategoryByName(String name) {
    if (categoryRepository.existsCategoryByName(name)) {
      throw new CustomException(CategoryErrorCode.CATEGORY_NAME_DUPLICATE);
    }
  }

  private Category findCategoryById(UUID id) {
    return categoryRepository.findCategoryById(id)
        .orElseThrow(() -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND));
  }

  private Category findParentCategoryOrNull(UUID parentId) {
    if (parentId == null) {
      return null;
    }
    return findCategoryById(parentId);
  }

  private void saveCategoryRecursively(CreateCategoriesRequest request, Category parent, List<Category> collector) {
    Category category = Category.builder()
        .name(request.name())
        .parentId(parent)
        .build();

    categoryRepository.save(category);
    collector.add(category);

    if (request.children() != null) {
      for (CreateCategoriesRequest child : request.children()) {
        saveCategoryRecursively(child, category, collector);
      }
    }
  }

  private List<CreateCategoriesResponse> buildCategoryTree(List<Category> allCategories) {
    Map<UUID, CreateCategoriesResponse> map = new HashMap<>();

    for (Category c : allCategories) {
      map.put(c.getId(), CreateCategoriesResponse.of(c));
    }

    List<CreateCategoriesResponse> roots = new ArrayList<>();
    for (Category c : allCategories) {
      CreateCategoriesResponse current = map.get(c.getId());

      if (c.getParentId() != null) {
        CreateCategoriesResponse parent = map.get(c.getParentId().getId());
        parent.getChildren().add(current);
      } else {
        roots.add(current);
      }
    }

    return roots;
  }

  private List<DeleteCategoryResponse> buildDeletedCategoryTree(List<Category> all) {
    Map<UUID, DeleteCategoryResponse> map = new HashMap<>();

    for (Category c : all) {
      map.put(c.getId(), DeleteCategoryResponse.of(c));
    }

    List<DeleteCategoryResponse> roots = new ArrayList<>();
    for (Category c : all) {
      DeleteCategoryResponse current = map.get(c.getId());

      if (c.getParentId() != null) {
        DeleteCategoryResponse parent = map.get(c.getParentId().getId());
        parent.getChildren().add(current);
      } else {
        roots.add(current);
      }
    }

    return roots;
  }


  private void collectAllNames(CreateCategoriesRequest request, Set<String> nameSet) {
    nameSet.add(request.name());
    if (request.children() != null) {
      for (CreateCategoriesRequest child : request.children()) {
        collectAllNames(child, nameSet);
      }
    }
  }

  private Category findCategoryByName(String name) {
    return categoryRepository.findCategoryByName(name)
        .orElseThrow(() -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND));
  }


  private GetCategoryResponse buildSubTree(Category root, List<Category> all) {
    GetCategoryResponse dto = GetCategoryResponse.of(root);

    for (Category candidate : all) {
      if (candidate.getParentId() != null &&
          candidate.getParentId().getId().equals(root.getId())) {
        dto.getChildren().add(buildSubTree(candidate, all));
      }
    }

    return dto;
  }

  private boolean isDescendant(Category category, Category possibleParent) {
    if (possibleParent == null) {
      return false;
    }
    if (possibleParent.getId().equals(category.getId())) {
      return true;
    }

    return isDescendant(category, possibleParent.getParentId());
  }

  private void collectAllDescendants(Category parent, List<Category> all, List<Category> result) {
    for (Category candidate : all) {
      if (candidate.getParentId() != null &&
          candidate.getParentId().getId().equals(parent.getId())) {
        result.add(candidate);
        collectAllDescendants(candidate, all, result);
      }
    }
  }

  @Transactional
  @Override
  public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
    existsCategoryByName(request.name());

    Category parent = findParentCategoryOrNull(request.parentId());

    Category category = Category.builder()
        .name(request.name())
        .parentId(parent)
        .build();

    Category saved = categoryRepository.save(category);

    return CreateCategoryResponse.from(saved);
  }

  @Transactional
  @Override
  public List<CreateCategoriesResponse> createCategories(List<CreateCategoriesRequest> requests) {
    Set<String> allNames = new HashSet<>();

    for (CreateCategoriesRequest request : requests) {
      collectAllNames(request, allNames);
    }

    List<String> duplicated = categoryRepository.findNamesIn(allNames);
    if (!duplicated.isEmpty()) {
      throw new CustomException(CategoryErrorCode.CATEGORY_NAME_DUPLICATE);
    }

    List<Category> savedCategories = new ArrayList<>();
    for (CreateCategoriesRequest request : requests) {
      saveCategoryRecursively(request, null, savedCategories);
    }

    return buildCategoryTree(savedCategories);
  }

  @Transactional(readOnly = true)
  @Override
  public GetCategoryResponse getCategory(GetCategoryRequest request) {
    Category root = findCategoryByName(request.name());

    List<Category> all = categoryRepository.findAll();

    return buildSubTree(root, all);
  }

  @Transactional
  @Override
  public GetCategoryResponse updateCategory(UUID id, UpdateCategoryRequest request) {
    Category categoryById = findCategoryById(id);
    Category newParent = request.newParentId() != null ? findCategoryById(request.newParentId()) : null;

    if (newParent != null && categoryById.getId().equals(newParent.getId())) {
      throw new CustomException(CategoryErrorCode.CANNOT_SET_SELF_AS_PARENT);
    }

    if (isDescendant(categoryById, newParent)) {
      throw new CustomException(CategoryErrorCode.CANNOT_SET_DESCENDANT_AS_PARENT);
    }
    categoryById.changeName(request.newName());
    categoryById.changeParent(newParent);

    List<Category> all = categoryRepository.findAll(); // 재귀 트리 구성용
    return buildSubTree(categoryById, all); // 수정된 카테고리 기준 트리 응답
  }

  @Transactional
  @Override
  public List<DeleteCategoryResponse> deleteCategory(CustomUserDetails userDetails, UUID id) {
    Category root = findCategoryById(id);

    List<Category> all = categoryRepository.findAll();

    List<Category> toDelete = new ArrayList<>();
    collectAllDescendants(root, all, toDelete);
    toDelete.add(root);

    toDelete.forEach(category -> category.markDeleted(userDetails.id()));

    List<Category> active = categoryRepository.findAllNotDeleted();
    return buildDeletedCategoryTree(active);
  }
}
