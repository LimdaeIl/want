package com.want.user.infrastructure.jpa;

import com.want.user.domain.repository.UserRepository;
import com.want.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
}
