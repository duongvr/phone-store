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

  /**
   * Get all categories
   */
  @Transactional
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Get category by ID
   */
  @Transactional
  public CategoryDTO getCategoryById(Long id) {
    Category category = categoryRepository.findById(id);
    return category != null ? toDTO(category) : null;
  }

  /**
   * Create new category
   */
  @Transactional
  public CategoryDTO createCategory(CategoryDTO dto) {
    // Check if category name already exists
    if (categoryRepository.findByName(dto.getName()) != null) {
      throw new IllegalArgumentException("Category name already exists");
    }

    Category category = new Category();
    category.name = dto.getName();
    category.description = dto.getDescription();
    category.icon = dto.getIcon();

    categoryRepository.persist(category);
    return toDTO(category);
  }

  /**
   * Update category
   */
  @Transactional
  public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
    Category category = categoryRepository.findById(id);
    if (category == null) {
      throw new IllegalArgumentException("Category not found");
    }

    // Check if new name already exists (and it's not the same category)
    if (!category.name.equals(dto.getName())) {
      if (categoryRepository.findByName(dto.getName()) != null) {
        throw new IllegalArgumentException("Category name already exists");
      }
    }

    category.name = dto.getName();
    category.description = dto.getDescription();
    category.icon = dto.getIcon();

    categoryRepository.persist(category);
    return toDTO(category);
  }

  /**
   * Delete category
   */
  @Transactional
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id);
    if (category == null) {
      throw new IllegalArgumentException("Category not found");
    }
    categoryRepository.delete(category);
  }

  private CategoryDTO toDTO(Category category) {
    return new CategoryDTO(
        category.id,
        category.name,
        category.description,
        category.icon);
  }
}
