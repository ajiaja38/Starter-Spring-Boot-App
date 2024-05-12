package com.spring.starter.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.starter.model.entity.Role;
import com.spring.starter.repository.RoleRepository;
import com.spring.starter.service.RoleService;
import com.spring.starter.utils.constant.ERole;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role getOrSave(ERole role) {
    Optional<Role> optionalRole = this.roleRepository.findByRole(role);

    if (optionalRole.isPresent()) {
      return optionalRole.get();
    }

    Role currentRole = Role.builder().role(role).build();

    return this.roleRepository.save(currentRole);
  }
  
}
