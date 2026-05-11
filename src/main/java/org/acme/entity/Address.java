package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Address extends PanacheEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  public User user;

  @Column(nullable = false, length = 100)
  public String name; // Nhà riêng, Công ty, ...

  @Column(nullable = false, length = 100)
  public String fullName; // Tên người nhận

  @Column(nullable = false, length = 20)
  public String phone;

  @Column(nullable = false, length = 255)
  public String address; // Địa chỉ chi tiết

  @Column(nullable = false, length = 50)
  public String city; // Tỉnh/Thành phố

  @Column(nullable = false, length = 50)
  public String district; // Quận/Huyện

  @Column(nullable = false, length = 50)
  public String ward; // Phường/Xã

  @Column(length = 20)
  public String postalCode;

  @Column(name = "is_default", nullable = false)
  public Boolean isDefault = false;

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
