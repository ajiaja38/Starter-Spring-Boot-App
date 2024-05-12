package com.spring.starter.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.starter.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String username);
  Page<User> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrAddressIgnoreCaseContaining(String name, String email, String address, Pageable pageable);
}
