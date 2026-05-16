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
        // Lưu ý: Bạn cần định nghĩa thêm hàm updatePromotion trong PromotionService
        // Promotion updated = promotionService.updatePromotion(id, promotion);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePromotion(@PathParam("id") Long id) {
        // Lưu ý: Bạn cần định nghĩa thêm hàm deletePromotion trong PromotionService
        // promotionService.deletePromotion(id);
        return Response.noContent().build();
    }

    // ==========================================
    // CÁC ENDPOINT CŨ
    // ==========================================

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
}