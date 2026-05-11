package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
  public Long id;
  public String name;
  public String description;
  public Double price;
  public Double originalPrice;
  public Integer discount;
  public String imageUrl;
  public Integer stock;
  public String brand;
  public Double rating;
  public Integer reviewCount;
  public Long categoryId;
  public String specifications;
  public String warranty;
  public Boolean featured;
}
