package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Location;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.acme.service.ProductService;
// Giả định bạn có các service sau, hãy đổi tên package cho đúng với dự án của bạn
import org.acme.service.CategoryService;
//import org.acme.service.OrderService;
//import org.acme.service.PromotionService;

import java.util.List;

@Path("/")
@Blocking
public class PageController {

    // 1. INJECT CÁC TEMPLATE
    @Inject Template index;
    @Inject Template products;
    @Inject @Location("product-detail") Template productDetail;
    @Inject Template cart;
    @Inject Template checkout;
    @Inject Template login;
    @Inject Template register;
    @Inject Template profile;
    @Inject Template addresses;
    @Inject Template orders;
    @Inject Template promotions;

    @Inject @Location("admin/products") Template adminProducts;
    @Inject @Location("admin/orders") Template adminOrders;

    // 2. INJECT CÁC SERVICE ĐỂ LẤY DỮ LIỆU
    @Inject ProductService productService;
    @Inject CategoryService categoryService; // Cần thiết cho menu và sidebar
//    @Inject OrderService orderService;       // Cần thiết cho lịch sử đơn hàng
//    @Inject PromotionService promotionService;

    // --- USER ROUTES ---

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance homePage() {
        return index.data("featuredProducts", productService.getFeaturedProducts(8))
                .data("categories", categoryService.getAllCategories());
    }

    @GET
    @Path("products")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance productsPage(@QueryParam("category") Long categoryId) {
        Object productList;
        if (categoryId != null) {
            productList = productService.getProductsByCategory(categoryId, 0, 50);
        } else {
            productList = productService.getAllProducts(0, 50);
        }

        return products.data("products", productList)
                .data("categories", categoryService.getAllCategories());
    }

    @GET
    @Path("products/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance productDetailPage(@PathParam("id") Long id) {
        return productDetail.data("product", productService.getProductById(id))
                .data("relatedProducts", productService.getFeaturedProducts(4));
    }

//    @GET
//    @Path("promotions")
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance promotionsPage() {
//        return promotions.data("promotions", promotionService.getAllActivePromotions());
//    }

//    @GET
//    @Path("orders")
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance ordersPage() {
//        // Giả sử lấy đơn hàng của user đang login (cần Security Context nếu có)
//        return orders.data("orders", orderService.getAllOrders());
//    }

    @GET
    @Path("profile")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance profilePage() {
        // Truyền thông tin user hiện tại vào đây
        return profile.data("user", "Thông tin user");
    }

    // --- CÁC TRANG TĨNH HOẶC CHƯA CÓ SERVICE ---

    @GET
    @Path("cart")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance cartPage() {
        return cart.instance(); // Thường xử lý bằng Javascript/LocalStorage ở Frontend
    }

    @GET
    @Path("checkout")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance checkoutPage() {
        return checkout.instance();
    }

    @GET
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginPage() {
        return login.instance();
    }

    @GET
    @Path("register")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance registerPage() {
        return register.instance();
    }

    // --- ADMIN MAPPING ---

    @GET
    @Path("admin/products")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminProductsPage() {
        // Admin lấy toàn bộ để quản lý
        return adminProducts.data("products", productService.getAllProducts(0, 100));
    }

//    @GET
//    @Path("admin/orders")
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance adminOrdersPage() {
//        return adminOrders.data("orders", orderService.getAllOrders());
//    }
}