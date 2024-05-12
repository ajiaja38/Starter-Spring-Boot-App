package com.spring.starter.model.entity;

import java.util.UUID;

import com.spring.starter.utils.constant.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
  
  @Id
  @Column(name = "role_id")
  private String id;

  @Enumerated(EnumType.STRING)
  private ERole role;

  @PrePersist
  public void prefixId() {
    this.id = "role-" + UUID.randomUUID();
  }

}
