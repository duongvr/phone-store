package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AddressDTO;
import org.acme.service.AddressService;

import java.util.List;

@Path("/api/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

  @Inject
  AddressService addressService;

  /**
   * Get all addresses for a user
   */
  @GET
  @Path("/user/{userId}")
  public Response getUserAddresses(@PathParam("userId") Long userId) {
    try {
      List<AddressDTO> addresses = addressService.getUserAddresses(userId);
      return Response.ok(addresses).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error fetching addresses: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Get address by ID
   */
  @GET
  @Path("/{id}")
  public Response getAddressById(@PathParam("id") Long id) {
    try {
      AddressDTO address = addressService.getAddressById(id);
      if (address == null) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse("Address not found"))
            .build();
      }
      return Response.ok(address).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error fetching address: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Get default address for user
   */
  @GET
  @Path("/user/{userId}/default")
  public Response getDefaultAddress(@PathParam("userId") Long userId) {
    try {
      AddressDTO address = addressService.getDefaultAddress(userId);
      if (address == null) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse("No default address found"))
            .build();
      }
      return Response.ok(address).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error fetching default address: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Create new address
   */
  @POST
  @Path("/user/{userId}")
  public Response createAddress(@PathParam("userId") Long userId, AddressDTO dto) {
    try {
      if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Full name is required"))
            .build();
      }
      if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Phone is required"))
            .build();
      }
      if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Address is required"))
            .build();
      }

      AddressDTO created = addressService.createAddress(userId, dto);
      return Response.status(Response.Status.CREATED).entity(created).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error creating address: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Update address
   */
  @PUT
  @Path("/{id}")
  public Response updateAddress(@PathParam("id") Long id, AddressDTO dto) {
    try {
      AddressDTO updated = addressService.updateAddress(id, dto);
      return Response.ok(updated).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error updating address: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Delete address
   */
  @DELETE
  @Path("/{id}")
  public Response deleteAddress(@PathParam("id") Long id) {
    try {
      addressService.deleteAddress(id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error deleting address: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Set address as default
   */
  @POST
  @Path("/{id}/set-default")
  public Response setDefaultAddress(@PathParam("id") Long id, SetDefaultRequest request) {
    try {
      if (request.getUserId() == null) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("User ID is required"))
            .build();
      }

      addressService.setDefaultAddress(request.getUserId(), id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Error setting default address: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Set default request class
   */
  public static class SetDefaultRequest {
    public Long userId;

    public Long getUserId() {
      return userId;
    }

    public void setUserId(Long userId) {
      this.userId = userId;
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
