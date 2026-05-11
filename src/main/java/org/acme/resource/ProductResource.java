package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ProductDTO;
import org.acme.service.ProductService;

// Thêm import này:
import io.smallrye.common.annotation.Blocking;

import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Blocking
public class ProductResource {

  @Inject
  ProductService productService;


  @GET

  public Response getAllProducts(
          @QueryParam("page") @DefaultValue("0") int page,
          @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
    try {
      List<ProductDTO> products = productService.getAllProducts(page, pageSize);
      return Response.ok(products).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error fetching products: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Get products by category
   */
  @GET
  @Path("/category/{categoryId}")
  public Response getProductsByCategory(
          @PathParam("categoryId") Long categoryId,
          @QueryParam("page") @DefaultValue("0") int page,
          @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
    try {
      List<ProductDTO> products = productService.getProductsByCategory(categoryId, page, pageSize);
      return Response.ok(products).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error fetching products: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Search products
   */
  @GET
  @Path("/search")
  public Response searchProducts(
          @QueryParam("keyword") String keyword,
          @QueryParam("page") @DefaultValue("0") int page,
          @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
    try {
      if (keyword == null || keyword.isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Search keyword is required"))
                .build();
      }

      List<ProductDTO> products = productService.searchProducts(keyword, page, pageSize);
      return Response.ok(products).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error searching products: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Get featured products
   */
  @GET
  @Path("/featured")
  public Response getFeaturedProducts(
          @QueryParam("limit") @DefaultValue("10") int limit) {
    try {
      List<ProductDTO> products = productService.getFeaturedProducts(limit);
      return Response.ok(products).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error fetching featured products: " + e.getMessage()))
              .build();
    }
  }

  @GET
  @Path("/{id}")
  public Response getProductById(@PathParam("id") Long id) {
    try {
      ProductDTO product = productService.getProductById(id);
      if (product == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("Product not found"))
                .build();
      }
      return Response.ok(product).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error fetching product: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Create new product (admin only)
   */
  @POST
  public Response createProduct(ProductDTO dto) {
    try {
      if (dto.getName() == null || dto.getName().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Product name is required"))
                .build();
      }
      if (dto.getPrice() == null || dto.getPrice() <= 0) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Product price must be greater than 0"))
                .build();
      }
      if (dto.getCategoryId() == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Category ID is required"))
                .build();
      }

      ProductDTO created = productService.createProduct(dto);
      return Response.status(Response.Status.CREATED).entity(created).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity(new ErrorResponse(e.getMessage()))
              .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error creating product: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Update product (admin only)
   */
  @PUT
  @Path("/{id}")
  public Response updateProduct(@PathParam("id") Long id, ProductDTO dto) {
    try {
      if (dto.getName() == null || dto.getName().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Product name is required"))
                .build();
      }
      if (dto.getPrice() == null || dto.getPrice() <= 0) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Product price must be greater than 0"))
                .build();
      }
      if (dto.getCategoryId() == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Category ID is required"))
                .build();
      }

      ProductDTO updated = productService.updateProduct(id, dto);
      return Response.ok(updated).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity(new ErrorResponse(e.getMessage()))
              .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error updating product: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Delete product (admin only)
   */
  @DELETE
  @Path("/{id}")
  public Response deleteProduct(@PathParam("id") Long id) {
    try {
      productService.deleteProduct(id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity(new ErrorResponse(e.getMessage()))
              .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error deleting product: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Update product stock (admin only)
   */
  @PATCH
  @Path("/{id}/stock")
  public Response updateStock(@PathParam("id") Long id, StockUpdateRequest request) {
    try {
      if (request.getQuantity() == null || request.getQuantity() < 0) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Quantity must be non-negative"))
                .build();
      }

      productService.updateStock(id, request.getQuantity());
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity(new ErrorResponse(e.getMessage()))
              .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity(new ErrorResponse("Error updating stock: " + e.getMessage()))
              .build();
    }
  }

  /**
   * Stock update request class
   */
  public static class StockUpdateRequest {
    public Integer quantity;

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }
  }


  public static class ErrorResponse {
    public String message;

    public ErrorResponse(String message) {
      this.message = message;
    }
  }
}