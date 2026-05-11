package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.ProductDTO;
import org.acme.entity.Category;
import org.acme.entity.Product;
import org.acme.repository.CategoryRepository;
import org.acme.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {

  @Inject
  ProductRepository productRepository;

  @Inject
  CategoryRepository categoryRepository;

  /**
   * Get all products with pagination
   */
  @Transactional
  public List<ProductDTO> getAllProducts(int page, int pageSize) {
    return productRepository.findAll()
        .page(page, pageSize)
        .list()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Get products by category
   */
  @Transactional
  public List<ProductDTO> getProductsByCategory(Long categoryId, int page, int pageSize) {
    return productRepository.find("category.id", categoryId)
        .page(page, pageSize)
        .list()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Search products by name
   */
  @Transactional
  public List<ProductDTO> searchProducts(String keyword, int page, int pageSize) {
    return productRepository.find("name like ?1", "%" + keyword + "%")
        .page(page, pageSize)
        .list()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Get featured products
   */
  @Transactional
  public List<ProductDTO> getFeaturedProducts(int limit) {
    return productRepository.find("featured", true)
        .page(0, limit)
        .list()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Get product by ID
   */
  @Transactional
  public ProductDTO getProductById(Long id) {
    Product product = productRepository.findById(id);
    return product != null ? toDTO(product) : null;
  }

  /**
   * Create new product
   */
  @Transactional
  public ProductDTO createProduct(ProductDTO dto) {
    Category category = categoryRepository.findById(dto.getCategoryId());
    if (category == null) {
      throw new IllegalArgumentException("Category not found");
    }

    Product product = new Product();
    product.name = dto.getName();
    product.description = dto.getDescription();
    product.price = dto.getPrice();
    product.originalPrice = dto.getOriginalPrice();
    product.discount = dto.getDiscount();
    product.imageUrl = dto.getImageUrl();
    product.stock = dto.getStock();
    product.brand = dto.getBrand();
    product.category = category;
    product.specifications = dto.getSpecifications();
    product.warranty = dto.getWarranty();
    product.featured = dto.getFeatured();

    productRepository.persist(product);
    return toDTO(product);
  }

  /**
   * Update product
   */
  @Transactional
  public ProductDTO updateProduct(Long id, ProductDTO dto) {
    Product product = productRepository.findById(id);
    if (product == null) {
      throw new IllegalArgumentException("Product not found");
    }

    Category category = categoryRepository.findById(dto.getCategoryId());
    if (category == null) {
      throw new IllegalArgumentException("Category not found");
    }

    product.name = dto.getName();
    product.description = dto.getDescription();
    product.price = dto.getPrice();
    product.originalPrice = dto.getOriginalPrice();
    product.discount = dto.getDiscount();
    product.imageUrl = dto.getImageUrl();
    product.stock = dto.getStock();
    product.brand = dto.getBrand();
    product.category = category;
    product.specifications = dto.getSpecifications();
    product.warranty = dto.getWarranty();
    product.featured = dto.getFeatured();

    productRepository.persist(product);
    return toDTO(product);
  }

  /**
   * Delete product
   */
  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id);
    if (product == null) {
      throw new IllegalArgumentException("Product not found");
    }
    productRepository.delete(product);
  }

  /**
   * Update product stock
   */
  @Transactional
  public void updateStock(Long id, Integer quantity) {
    Product product = productRepository.findById(id);
    if (product == null) {
      throw new IllegalArgumentException("Product not found");
    }
    product.stock = quantity;
    productRepository.persist(product);
  }

  private ProductDTO toDTO(Product product) {
    return new ProductDTO(
        product.id,
        product.name,
        product.description,
        product.price,
        product.originalPrice,
        product.discount,
        product.imageUrl,
        product.stock,
        product.brand,
        product.rating,
        product.reviewCount,
        product.category != null ? product.category.id : null,
        product.specifications,
        product.warranty,
        product.featured);
  }
}
