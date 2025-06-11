package com.want.product.domain.entity.productPolicy;

import com.want.common.audit.BaseEntity;
import com.want.common.exception.CustomException;
import com.want.company.domain.entity.Company;
import com.want.product.domain.entity.product.Product;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "p_products_policies")
@Entity
public class ProductPolicy extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private UUID id;

  @OneToMany(mappedBy = "productPolicy", fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "discount_type", nullable = false)
  private DiscountType discountType;

  @Column(name = "value", nullable = false)
  private Integer value;

  @Column(name = "started_at", nullable = false)
  private LocalDateTime startedAt;

  @Column(name = "ended_at", nullable = false)
  private LocalDateTime endedAt;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @Column(name = "min_purchase_amount", nullable = false)
  private Integer minPurchaseAmount;

  public boolean isCurrentlyActive() {
    LocalDateTime now = LocalDateTime.now();

    return isActive
        && now.isAfter(startedAt)
        && now.isBefore(endedAt);
  }

  public void addProduct(Product product) {
    this.products.add(product);
    product.applyProductPolicy(this);
  }

  public void assignCompany(Company company) {
    this.company = company;
  }

  public void updateName(String name) {
    if (name == null || name.isEmpty()) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_NAME_BLANK);
    }
    this.name = name;
  }

  public void updateDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_DESCRIPTION_BLANK);
    }
    this.description = description;
  }

  public void updateDiscountType(DiscountType discountType) {
    this.discountType = discountType;
  }

  public void updateValue(Integer value) {
    if (value == null) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_BLANK);
    }

    if (value < 0) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_NEGATIVE);
    }

    if (value > 100) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_VALUE_INVALID);
    }

    this.value = value;
  }

  public void updateStartedAt(LocalDateTime startedAt) {
    if (startedAt == null) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_STARTED_AT_BLANK);
    }

    LocalDateTime now = LocalDateTime.now();

    if (startedAt.isBefore(now)) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_START_DATE_IN_PAST);
    }
    this.startedAt = startedAt;
  }

  public void updateEndedAt(LocalDateTime endedAt) {
    if (endedAt == null) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_ENDED_AT_BLANK);
    }

    if (endedAt.isBefore(startedAt)) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_DATE_INVALID);
    }

    this.endedAt = endedAt;
  }

  public void updateIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public void updateMinPurchaseAmount(Integer minPurchaseAmount) {
    if (minPurchaseAmount == null) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_MIN_PURCHASE_AMOUNT_BLANK);
    }
    if (minPurchaseAmount < 0) {
      throw new CustomException(ProductPolicyErrorCode.POLICY_MIN_PURCHASE_AMOUNT_NEGATIVE);
    }
    this.minPurchaseAmount = minPurchaseAmount;
  }
}
