package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ReviewDTO;
import org.acme.service.ReviewService;

import java.util.List;

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    ReviewService reviewService;

    // GET: /api/reviews/product/{productId}
    @GET
    @Path("/product/{productId}")
    public Response getReviewsByProduct(@PathParam("productId") Long productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProduct(productId);
        return Response.ok(reviews).build();
    }

    // POST: /api/reviews
    @POST
    public Response addReview(ReviewDTO dto) {
        ReviewDTO createdReview = reviewService.addReview(dto);
        return Response.status(Response.Status.CREATED).entity(createdReview).build();
    }

    // DELETE: /api/reviews/{id}
    @DELETE
    @Path("/{id}")
    public Response deleteReview(@PathParam("id") Long id) {
        reviewService.deleteReview(id);
        return Response.noContent().build();
    }
}