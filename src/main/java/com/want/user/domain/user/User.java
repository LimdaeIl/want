package com.want.user.domain.user;

import com.want.common.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "p_users")
@Entity
public class User extends BaseEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "name", nullable = true)
  private String name;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "profile_image")
  private String profileImage;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role = Role.ROLE_CUSTOMER;
}
