package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.News;
import org.acme.service.NewsService;

import java.util.List;

@Path("/api/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {

    @Inject
    NewsService newsService;

    @GET
    public Response getAllNews() {
        return Response.ok(newsService.getAllNews()).build();
    }

    @GET
    @Path("/{id}")
    public Response getNewsById(@PathParam("id") Long id) {
        News news = newsService.getNewsById(id);
        if (news == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(news).build();
    }

    @POST
    public Response createNews(News news) {
        News created = newsService.createNews(news);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateNews(@PathParam("id") Long id, News news) {
        News updated = newsService.updateNews(id, news);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNews(@PathParam("id") Long id) {
        newsService.deleteNews(id);
        return Response.noContent().build();
    }
}
