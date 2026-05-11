package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Category extends PanacheEntity {

  @Column(unique = true, nullable = false, length = 100)
  public String name;

  @Column(columnDefinition = "TEXT")
  public String description;

  @Column(length = 50)
  public String icon;

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
