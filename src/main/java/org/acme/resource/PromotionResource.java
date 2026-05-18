package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Promotion;
import org.acme.service.PromotionService;

import java.util.List;

@Path("/api/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionResource {

    @Inject
    PromotionService promotionService;

    // ==========================================
    // CÁC ENDPOINT MỚI BỔ SUNG
    // ==========================================

    @PUT
    @Path("/{id}")
    public Response updatePromotion(@PathParam("id") Long id, Promotion promotion) {
        Promotion updated = promotionService.updatePromotion(id, promotion);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePromotion(@PathParam("id") Long id) {
        promotionService.deletePromotion(id);
        return Response.noContent().build();
    }

    // ==========================================
    // CÁC ENDPOINT CŨ
    // ==========================================

    @GET
    @Path("/validate")
    public Response validatePromotion(@QueryParam("code") String code) {
        try {
            Promotion promo = promotionService.validatePromotion(code);
            return Response.ok(promo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    public Response getPromotions() {
        List<Promotion> promotions = promotionService.getPromotions();
        return Response.ok(promotions).build();
    }

    @GET
    @Path("/product/{productId}")
    public Response getPromotionInProduct(@PathParam("productId") Long productId) {
        List<Promotion> promotions = promotionService.getPromotionInProduct(productId);
        return Response.ok(promotions).build();
    }

    @POST
    public Response createPromotion(Promotion promotion) {
        Promotion createdPromotion = promotionService.createPromotion(promotion);
        return Response.status(Response.Status.CREATED).entity(createdPromotion).build();
    }

    public static class ErrorResponse {
        public String message;
        public ErrorResponse(String message) { this.message = message; }
    }
}