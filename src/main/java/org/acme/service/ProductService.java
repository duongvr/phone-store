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

  @Transactional
  public List<ProductDTO> getAllProducts(int page, int pageSize) {
    return productRepository.findAll()
            .page(page, pageSize)
            .list()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public List<ProductDTO> getProductsByCategory(Long categoryId, int page, int pageSize) {
    return productRepository.find("category.id", categoryId)
            .page(page, pageSize)
            .list()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public List<ProductDTO> searchProducts(String keyword, int page, int pageSize) {
    return productRepository.find("name like ?1", "%" + keyword + "%")
            .page(page, pageSize)
            .list()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public List<ProductDTO> getFeaturedProducts(int limit) {
    return productRepository.find("featured", true)
            .page(0, limit)
            .list()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public ProductDTO getProductById(Long id) {
    Product product = productRepository.findById(id);
    return product != null ? toDTO(product) : null;
  }

  @Transactional
  public ProductDTO createProduct(ProductDTO dto) {
    Category category = categoryRepository.findById(dto.getCategoryId());
    if (category == null) {
      throw new IllegalArgumentException("Không tìm thấy danh mục");
    }

    Product product = new Product();
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setOriginalPrice(dto.getOriginalPrice());
    product.setDiscount(dto.getDiscount() != null ? dto.getDiscount() : 0);
    product.setImageUrl(dto.getImageUrl());
    product.setStock(dto.getStock() != null ? dto.getStock() : 0);
    product.setBrand(dto.getBrand());
    product.setCategory(category);
    product.setSpecifications(dto.getSpecifications());
    product.setWarranty(dto.getWarranty());
    product.setFeatured(dto.getFeatured() != null ? dto.getFeatured() : false);

    productRepository.persist(product);
    return toDTO(product);
  }

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

    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setOriginalPrice(dto.getOriginalPrice());
    product.setDiscount(dto.getDiscount() != null ? dto.getDiscount() : 0);
    product.setImageUrl(dto.getImageUrl());
    product.setStock(dto.getStock() != null ? dto.getStock() : 0);
    product.setBrand(dto.getBrand());
    product.setCategory(category);
    product.setSpecifications(dto.getSpecifications());
    product.setWarranty(dto.getWarranty());
    product.setFeatured(dto.getFeatured() != null ? dto.getFeatured() : false);

    productRepository.persist(product);
    return toDTO(product);
  }

  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id);
    if (product == null) {
      throw new IllegalArgumentException("Product not found");
    }
    productRepository.delete(product);
  }

  private ProductDTO toDTO(Product product) {
    ProductDTO dto = new ProductDTO();
    dto.setId(product.id);
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setOriginalPrice(product.getOriginalPrice());
    dto.setDiscount(product.getDiscount());
    dto.setImageUrl(product.getImageUrl());
    dto.setStock(product.getStock());
    dto.setBrand(product.getBrand());
    dto.setCategoryId(product.getCategory().id);
    dto.setSpecifications(product.getSpecifications());
    dto.setWarranty(product.getWarranty());
    dto.setFeatured(product.getFeatured());
    return dto;
  }

    public void updateStock(Long id, Integer quantity) {
    }
}
