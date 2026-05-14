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
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private Double price;

  @Column(name = "original_price")
  private Double originalPrice;

  @Column(name = "discount")
  private Integer discount = 0;

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @Column(nullable = false)
  private Integer stock = 0;

  @Column(length = 50)
  private String brand;

  @Column(name = "rating")
  private Double rating = 0.0;

  @Column(name = "review_count")
  private Integer reviewCount = 0;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(columnDefinition = "JSON")
  private String specifications;

  @Column(columnDefinition = "TEXT")
  private String warranty;

  @Column(nullable = false)
  private Boolean featured = false;

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
