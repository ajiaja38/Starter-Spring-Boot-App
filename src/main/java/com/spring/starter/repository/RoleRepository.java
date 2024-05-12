package com.spring.starter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.starter.model.entity.Role;
import com.spring.starter.utils.constant.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByRole(ERole role);
}
