package com.want.order.domain.entity;

import com.want.common.audit.BaseEntity;
import com.want.common.exception.CustomException;
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
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "p_order_items")
@Entity
public class OrderProduct extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false, unique = true)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "order_quantity", nullable = false)
  private Integer orderQuantity;

  @Column(name = "order_price", nullable = false)
  private Integer orderPrice;

  public void assignOrder(Order order) {
    this.order = order;
  }

  public static OrderProduct of(Product product, Integer quantity, Integer price) {
    return OrderProduct.builder()
        .product(product)
        .orderQuantity(quantity)
        .orderPrice(price)
        .build();
  }

  public static OrderProduct validateAndCreate(Product product, int quantity) {
    if (quantity <= 0) {
      throw new CustomException(OrderErrorCode.ORDER_QUANTITY_INVALID_NEGATIVE);
    }

    product.decreaseStock(quantity);

    int totalPrice = product.getPrice() * quantity;

    return OrderProduct.builder()
        .product(product)
        .orderQuantity(quantity)
        .orderPrice(totalPrice)
        .build();
  }

}