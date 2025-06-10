package com.want.product.domain.entity.productPolicy;

import com.want.common.audit.BaseEntity;
import com.want.product.domain.entity.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "name", nullable = false)
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

  @Column(name = "min_purchase_amount")
  private Integer minPurchaseAmount;

  @Column(name = "stackable_with_coupon")
  private Boolean stackableWithCoupon;

  public boolean isCurrentlyActive() {
    LocalDateTime now = LocalDateTime.now();

    return isActive
        && now.isAfter(startedAt)
        && now.isBefore(endedAt);
  }

}
