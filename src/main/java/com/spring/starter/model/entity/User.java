package com.spring.starter.model.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  
  @Id
  @Column(name = "user_id")
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  private String password;

  @Column( unique = true)
  private String phone;

  private String email;

  private String address;

  private String avatar;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
  private Date birthDate;

  private Date createdAt;

  private Date updatedAt;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "t_user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  @PrePersist
  public void prefixId() {
    this.id = "user-" + UUID.randomUUID();
  }

}
