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
  private User user;

  @Column(nullable = false, length = 100)
  private String name; // Nhà riêng, Công ty, ...

  @Column(nullable = false, length = 100)
  private String fullName; // Tên người nhận

  @Column(nullable = false, length = 20)
  private String phone;

  @Column(nullable = false, length = 255)
  private String address; // Địa chỉ chi tiết

  @Column(nullable = false, length = 50)
  private String city; // Tỉnh/Thành phố

  @Column(nullable = false, length = 50)
  private String district; // Quận/Huyện

  @Column(nullable = false, length = 50)
  private String ward; // Phường/Xã

  @Column(length = 20)
  private String postalCode;

  @Column(name = "is_default", nullable = false)
  private Boolean isDefault = false;

  @Column(name = "created_at")
  private Long createdAt;

  @Column(name = "updated_at")
  private Long updatedAt;

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
