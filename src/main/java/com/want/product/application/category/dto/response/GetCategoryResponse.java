package com.want.product.application.category.dto.response;

import com.want.product.domain.entity.category.Category;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GetCategoryResponse {
  private UUID id;
  private String name;
  private UUID parentId;
  private LocalDateTime deletedAt;
  private Long deletedBy;
  private List<GetCategoryResponse> children = new ArrayList<>();

  public static GetCategoryResponse of(Category category) {
    return GetCategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .parentId(category.getParentId() != null ? category.getParentId().getId() : null)
        .deletedAt(category.getDeletedAt())
        .deletedBy(category.getDeletedBy())
        .children(new ArrayList<>())
        .build();
  }
}