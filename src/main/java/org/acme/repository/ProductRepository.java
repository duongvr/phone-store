package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Product;

import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

  public List<Product> findByCategory(Long categoryId) {
    return find("category.id", categoryId).list();
  }

  public List<Product> findFeatured() {
    return find("featured", true).list();
  }

  public List<Product> searchByName(String name) {
    return find("name like ?1", "%" + name + "%").list();
  }

  public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
    return find("price between ?1 and ?2", minPrice, maxPrice).list();
  }
}
