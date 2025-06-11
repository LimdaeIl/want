package com.want.product.domain.entity.product;


import com.want.common.audit.BaseEntity;
import com.want.company.domain.entity.Company;
import com.want.product.domain.entity.category.Category;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "p_products")
@Entity
public class Product extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_policy_id")
  private ProductPolicy productPolicy;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private Integer price;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Enumerated(EnumType.STRING)
  @Column(name = "sale_status")
  private SaleStatus saleStatus;

  @Column(name = "description", nullable = false)
  private String description;

  public void registerCompany(Company company) {
    this.company = company;
  }

  public void applyProductPolicy(ProductPolicy productPolicy) {
    this.productPolicy = productPolicy;
  }
}
