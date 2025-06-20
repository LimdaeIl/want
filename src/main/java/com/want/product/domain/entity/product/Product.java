package com.want.product.domain.entity.product;


import com.want.common.audit.BaseEntity;
import com.want.common.exception.CustomException;
import com.want.company.domain.entity.Company;
import com.want.order.domain.entity.OrderProduct;
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

  @Builder.Default
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  private List<OrderProduct> orderProducts = new ArrayList<>();

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

  public void applyCategory(Category category) {
    this.category = category;
  }

  public void updateName(String name) {
    if (name == null || name.isEmpty()) {
      throw new CustomException(ProductErrorCode.PRODUCT_NAME_BLANK);
    }

    this.name = name;
  }

  public void updatePrice(Integer price) {
    if (price == null || price < 0) {
      throw new CustomException(ProductErrorCode.PRODUCT_PRICE_INVALID);
    }

    this.price = price;
  }

  public void updateQuantity(Integer quantity) {
    if (quantity == null || quantity < 0) {
      throw new CustomException(ProductErrorCode.PRODUCT_QUANTITY_INVALID);
    }
    this.quantity = quantity;
  }

  public void updateSaleStatus(SaleStatus saleStatus) {
    this.saleStatus = saleStatus;
  }

  public void updateDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new CustomException(ProductErrorCode.PRODUCT_DESCRIPTION_BLANK);
    }
    this.description = description;
  }

  public void updateThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public void decreaseStock(int quantity) {
    if (this.quantity < quantity) {
      throw new CustomException(ProductErrorCode.PRODUCT_QUANTITY_INVALID);
    }
    this.quantity -= quantity;
  }

  public void increaseStock(int quantity) {
    this.quantity += quantity;
  }
}
