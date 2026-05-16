package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CategoryDTO;
import org.acme.entity.Category;
import org.acme.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryService {

  @Inject
  CategoryRepository categoryRepository;

  @Transactional
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.listAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public CategoryDTO getCategoryById(Long id) {
    Category category = categoryRepository.findById(id);
    return category != null ? toDTO(category) : null;
  }

  @Transactional
  public CategoryDTO createCategory(CategoryDTO dto) {
    // Check if category name already exists
    if (categoryRepository.findByName(dto.getName()) != null) {
      throw new IllegalArgumentException("Tên danh mục đã tồn tại");
    }

    Category category = new Category();
    category.setName(dto.getName());
    category.setDescription(dto.getDescription());
    category.setIcon(dto.getIcon());

    categoryRepository.persist(category);
    return toDTO(category);
  }

  @Transactional
  public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
    Category category = categoryRepository.findById(id);
    if (category == null) {
      throw new IllegalArgumentException("Không tìm thấy danh mục");
    }

    if (!category.getName().equals(dto.getName())) {
      if (categoryRepository.findByName(dto.getName()) != null) {
        throw new IllegalArgumentException("Tên danh mục đã tồn tại");
      }
    }

    category.setName(dto.getName());
    category.setDescription(dto.getDescription());
    category.setIcon(dto.getIcon());

    categoryRepository.persist(category);
    return toDTO(category);
  }

  @Transactional
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id);
    if (category == null) {
      throw new IllegalArgumentException("Không tìm thấy danh mục");
    }
    categoryRepository.delete(category);
  }

  private CategoryDTO toDTO(Category category) {
    CategoryDTO dto = new CategoryDTO();
    dto.setId(category.id);
    dto.setName(category.getName());
    dto.setDescription(category.getDescription());
    dto.setIcon(category.getIcon());
    return dto;
  }
}
