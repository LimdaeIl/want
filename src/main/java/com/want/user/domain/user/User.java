package com.want.user.domain.user;

import com.want.common.audit.BaseEntity;
import com.want.common.exception.CustomException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private List<UserSignInHistory> signInHistories = new ArrayList<>();

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "name")
  private String name;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "profile_image")
  private String profileImage;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role = Role.ROLE_CUSTOMER;

  public void addSignInHistory(UserSignInHistory history) {
    if (!signInHistories.contains(history)) {
      signInHistories.add(history);
      history.setUser(this);
    }
  }

  public void updateEmail(String newEmail) {
    if (this.email.equals(newEmail)) {
      throw new CustomException(UserErrorCode.USER_EMAIL_SAME_AS_OLD);
    }

    if (newEmail == null || newEmail.isEmpty()) {
      throw new CustomException(UserErrorCode.USER_EMAIL_BLANK);
    }

    this.email = newEmail;
  }

  public void updatePassword(String newPassword) {
    if (newPassword == null || newPassword.isEmpty()) {
      throw new CustomException(UserErrorCode.USER_PASSWORD_BLANK);
    }

    this.password = newPassword;
  }

  public void updateName(String name) {
    if (name == null || name.isEmpty()) {
      throw new CustomException(UserErrorCode.USER_NAME_INVALID);
    }
    this.name = name;
  }

  public void updateProfileImage(String newProfileImage) {
    if (newProfileImage == null || newProfileImage.isBlank()) {
      this.profileImage = null;
      return;
    }

    this.profileImage = newProfileImage;
  }

  public void updateRole(Role role) {
    if (role == null) {
      throw new CustomException(UserErrorCode.USER_ROLE_INVALID);
    }

    this.role = role;
  }

  public void updatePhone(String newPhone) {
    if (newPhone == null || newPhone.isBlank()) {
      throw new CustomException(UserErrorCode.USER_PHONE_BLANK);
    }
    this.phone = newPhone;
  }

}
