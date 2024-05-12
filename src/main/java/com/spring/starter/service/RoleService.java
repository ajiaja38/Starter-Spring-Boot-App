package com.spring.starter.service;

import com.spring.starter.model.entity.Role;
import com.spring.starter.utils.constant.ERole;

public interface RoleService {

  Role getOrSave(ERole role);
  
}
