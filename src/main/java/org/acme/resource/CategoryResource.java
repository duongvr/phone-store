package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CategoryDTO;
import org.acme.service.CategoryService;

import java.util.List;

@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

  @Inject
  CategoryService categoryService;

  /**
   * Get all categories
   */
  @GET
  public Response getAllCategories() {
    try {
      List<CategoryDTO> categories = categoryService.getAllCategories();
      return Response.ok(categories).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error fetching categories: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Get category by ID
   */
  @GET
  @Path("/{id}")
  public Response getCategoryById(@PathParam("id") Long id) {
    try {
      CategoryDTO category = categoryService.getCategoryById(id);
      if (category == null) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse("Category not found"))
            .build();
      }
      return Response.ok(category).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error fetching category: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Create new category (admin only)
   */
  @POST
  public Response createCategory(CategoryDTO dto) {
    try {
      if (dto.getName() == null || dto.getName().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Category name is required"))
            .build();
      }

      CategoryDTO created = categoryService.createCategory(dto);
      return Response.status(Response.Status.CREATED).entity(created).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error creating category: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Update category (admin only)
   */
  @PUT
  @Path("/{id}")
  public Response updateCategory(@PathParam("id") Long id, CategoryDTO dto) {
    try {
      if (dto.getName() == null || dto.getName().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Category name is required"))
            .build();
      }

      CategoryDTO updated = categoryService.updateCategory(id, dto);
      return Response.ok(updated).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error updating category: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Delete category (admin only)
   */
  @DELETE
  @Path("/{id}")
  public Response deleteCategory(@PathParam("id") Long id) {
    try {
      categoryService.deleteCategory(id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error deleting category: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Error response class
   */
  public static class ErrorResponse {
    public String message;

    public ErrorResponse(String message) {
      this.message = message;
    }
  }
}
