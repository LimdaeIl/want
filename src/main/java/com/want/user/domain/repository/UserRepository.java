package com.want.user.domain.repository;

import com.want.user.domain.user.User;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findUserByEmail(String email);

  Optional<User> findUserByPhone(String phone);

  Optional<User> findUserById(Long id);

  boolean existsUserByEmail(String email);

  boolean existsUserByPhone(String phone);
}
