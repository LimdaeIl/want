package com.want.company.domain.entity;

import com.want.common.audit.BaseEntity;
import com.want.product.domain.entity.product.Product;
import com.want.product.domain.entity.productPolicy.ProductPolicy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "p_companies")
@Entity
public class Company extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  private UUID id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();

  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  private List<ProductPolicy> productPolicies = new ArrayList<>();

  public void addProductPolicy(ProductPolicy productPolicy) {
    this.productPolicies.add(productPolicy);
    productPolicy.assignCompany(this);
  }

  public void addProduct(Product product) {
    this.products.add(product);
    product.registerCompany(this);
  }


}
