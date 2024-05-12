package com.spring.starter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.starter.security.JwtUtils;
import com.spring.starter.service.HeaderService;

@Service
public class HeaderServiceImpl implements HeaderService {

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public String userIdByToken(String token) {
    return this.jwtUtils.getIdUserByAccessToken(token);
  }
  
}
