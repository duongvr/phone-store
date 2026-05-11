package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class User extends PanacheEntity {

  @Column(unique = true, nullable = false, length = 100)
  public String email;

  @Column(nullable = false, length = 255)
  public String password;

  @Column(nullable = false, length = 100)
  public String fullName;

  @Column(length = 20)
  public String phone;

  @Column(length = 255)
  public String avatar;

  @Column(nullable = false, length = 20)
  public String role = "USER"; // USER, ADMIN

  @Column(nullable = false)
  public Boolean active = true;

  @Column(name = "created_at")
  public Long createdAt;

  @Column(name = "updated_at")
  public Long updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = System.currentTimeMillis();
    updatedAt = System.currentTimeMillis();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = System.currentTimeMillis();
  }
}
