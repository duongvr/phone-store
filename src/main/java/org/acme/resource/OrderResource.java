package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CreateOrderRequestDTO;
import org.acme.dto.OrderDTO;
import org.acme.dto.OrderItemDTO;
import org.acme.service.OrderService;

import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    // ==========================================
    // CÁC ENDPOINT MỚI BỔ SUNG
    // ==========================================

    @POST
    public Response checkout(CreateOrderRequestDTO request) {
        OrderDTO createdOrder = orderService.createOrder(request);
        return Response.status(Response.Status.CREATED).entity(createdOrder).build();
    }

    @GET
    @Path("/user/{userId}")
    public Response getOrdersByUser(@PathParam("userId") Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUser(userId);
        return Response.ok(orders).build();
    }

    @PATCH
    @Path("/{id}/status")
    public Response updateStatus(@PathParam("id") Long id, @QueryParam("status") String status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return Response.ok(updatedOrder).build();
    }

    // ==========================================
    // CÁC ENDPOINT CŨ
    // ==========================================

    @GET
    public Response getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}/items")
    public Response getOrderItems(@PathParam("orderId") Long orderId) {
        List<OrderItemDTO> items = orderService.getOrderItemsByOrderId(orderId);
        return Response.ok(items).build();
    }
}