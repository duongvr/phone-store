package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CreateOrderRequestDTO;
import org.acme.dto.OrderDTO;
import org.acme.dto.OrderItemDTO;
import org.acme.service.OrderService;
import org.acme.util.JwtUtil;

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
    public Response checkout(@HeaderParam("Authorization") String authHeader, CreateOrderRequestDTO request) {
        // Validate token
        Long tokenUserId = extractUserIdFromToken(authHeader);
        if (tokenUserId == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Yêu cầu đăng nhập\"}").build();
        }
        // Gán userId từ token vào request để đảm bảo đúng người đặt
        request.setUserId(tokenUserId);
        OrderDTO createdOrder = orderService.createOrder(request);
        return Response.status(Response.Status.CREATED).entity(createdOrder).build();
    }

    @GET
    @Path("/user/{userId}")
    public Response getOrdersByUser(
            @PathParam("userId") Long userId,
            @HeaderParam("Authorization") String authHeader) {
        // Validate token
        Long tokenUserId = extractUserIdFromToken(authHeader);
        if (tokenUserId == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Yêu cầu đăng nhập\"}").build();
        }
        // Chỉ cho phép user xem đơn hàng của chính mình
        if (!tokenUserId.equals(userId)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\": \"Không có quyền truy cập\"}").build();
        }
        List<OrderDTO> orders = orderService.getOrdersByUser(userId);
        return Response.ok(orders).build();
    }

    @PATCH
    @Path("/{id}/status")
    public Response updateStatus(@PathParam("id") Long id, @QueryParam("status") String status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return Response.ok(updatedOrder).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Long id) {
        OrderDTO order = orderService.getOrderById(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Order not found\"}")
                    .build();
        }
        return Response.ok(order).build();
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

    // ==========================================
    // HELPER
    // ==========================================

    private Long extractUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        return JwtUtil.validateToken(token);
    }
}