package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends PanacheEntity {

  @Column(nullable = false, length = 150)
  public String name;

  @Column(columnDefinition = "TEXT")
  public String description;

  @Column(nullable = false)
  public Double price;

  @Column(name = "original_price")
  public Double originalPrice;

  @Column(name = "discount")
  public Integer discount = 0;

  @Column(name = "image_url", length = 255)
  public String imageUrl;

  @Column(nullable = false)
  public Integer stock = 0;

  @Column(length = 50)
  public String brand;

  @Column(name = "rating")
  public Double rating = 0.0;

  @Column(name = "review_count")
  public Integer reviewCount = 0;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  public Category category;

  @Column(columnDefinition = "JSON")
  public String specifications;

  @Column(columnDefinition = "TEXT")
  public String warranty;

  @Column(nullable = false)
  public Boolean featured = false;

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
