package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.OrderItemDTO;
import org.acme.service.OrderItemService;

@Path("/api/order-items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderItemResource {

    @Inject
    OrderItemService orderItemService;

    // POST: /api/order-items
    @POST
    public Response addOrderItem(OrderItemDTO dto) {
        OrderItemDTO createdItem = orderItemService.addOrderItem(dto);
        return Response.status(Response.Status.CREATED).entity(createdItem).build();
    }

    // PUT: /api/order-items/{id}/quantity?newQuantity=5
    @PUT
    @Path("/{id}/quantity")
    public Response updateQuantity(@PathParam("id") Long id, @QueryParam("newQuantity") int newQuantity) {
        OrderItemDTO updatedItem = orderItemService.updateQuantity(id, newQuantity);
        return Response.ok(updatedItem).build();
    }

    // DELETE: /api/order-items/{id}
    @DELETE
    @Path("/{id}")
    public Response deleteOrderItem(@PathParam("id") Long id) {
        orderItemService.deleteOrderItem(id);
        return Response.noContent().build(); // Trả về mã 204 No Content (Xóa thành công)
    }
}