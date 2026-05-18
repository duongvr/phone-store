package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Location;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.acme.service.ProductService;
import org.acme.service.CategoryService;
import org.acme.service.OrderService;
import org.acme.service.PromotionService;
import org.acme.service.UserService;
import org.acme.service.AddressService;
import org.acme.service.NewsService;

import java.util.List;

@Path("/")
@Blocking
public class PageController {

    // 1. INJECT CÁC TEMPLATE
    @Inject
    Template index;
    @Inject
    Template products;
    @Inject
    @Location("product-detail")
    Template productDetail;
    @Inject
    Template cart;
    @Inject
    Template checkout;
    @Inject
    Template login;
    @Inject
    Template register;
    @Inject
    Template profile;
    @Inject
    Template addresses;
    @Inject
    Template orders;
    @Inject
    @Location("order-detail")
    Template orderDetail;
    @Inject
    Template promotions;
    @Inject
    Template wishlist;
    @Inject
    Template news;

    @Inject
    @Location("admin/admin-dashboard")
    Template adminDashboard;
    @Inject
    @Location("admin/products")
    Template adminProducts;
    @Inject
    @Location("admin/admin-product-form")
    Template adminProductForm;
    @Inject
    @Location("admin/orders")
    Template adminOrders;
    @Inject
    @Location("admin/admin-order-detail")
    Template adminOrderDetail;
    @Inject
    @Location("admin/admin-users")
    Template adminUsers;
    @Inject
    @Location("admin/admin-promotions")
    Template adminPromotions;
    @Inject
    @Location("admin/admin-categories")
    Template adminCategories;

    // 2. INJECT CÁC SERVICE ĐỂ LẤY DỮ LIỆU
    @Inject
    ProductService productService;
    @Inject
    CategoryService categoryService;
    @Inject
    OrderService orderService;
    @Inject
    PromotionService promotionService;
    @Inject
    UserService userService;
    @Inject
    AddressService addressService;
    @Inject
    NewsService newsService;

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

    @GET
    @Path("promotions")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance promotionsPage() {
        return promotions.data("promotions", promotionService.getPromotions());
    }

    @GET
    @Path("orders")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance ordersPage() {
        return orders.data("orders", orderService.getAllOrders());
    }

    @GET
    @Path("orders/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance orderDetailPage(@PathParam("id") Long id) {
        return orderDetail.data("order", "Chi tiết đơn hàng " + id);
    }

    @GET
    @Path("profile")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance profilePage() {
        return profile.data("user", "Thông tin user");
    }

    @GET
    @Path("addresses")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance addressesPage() {
        return addresses.instance();
    }

    @GET
    @Path("wishlist")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance wishlistPage() {
        return wishlist.instance();
    }

    @GET
    @Path("news")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newsPage() {
        return news.data("newsList", newsService.getAllNews());
    }

    @GET
    @Path("cart")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance cartPage() {
        return cart.instance();
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
    @Path("admin/dashboard")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminDashboardPage() {
        return adminDashboard.instance();
    }

    @GET
    @Path("admin/products")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminProductsPage() {
        return adminProducts.data("products", productService.getAllProducts(0, 100));
    }

    @GET
    @Path("admin/products/add")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance addProductPage() {
        return adminProductForm.data("mode", "add");
    }

    @GET
    @Path("admin/products/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance editProductPage(@PathParam("id") Long id) {
        return adminProductForm.data("mode", "edit").data("product", productService.getProductById(id));
    }

    @GET
    @Path("admin/orders")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminOrdersPage() {
        return adminOrders.data("orders", orderService.getAllOrders());
    }

    @GET
    @Path("admin/orders/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminOrderDetailPage(@PathParam("id") Long id) {
        return adminOrderDetail.instance();
    }

    @GET
    @Path("admin/users")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminUsersPage() {
        return adminUsers.data("users", userService.getAllUsers());
    }

    @GET
    @Path("admin/promotions")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminPromotionsPage() {
        return adminPromotions.data("promotions", promotionService.getPromotions());
    }

    @GET
    @Path("admin/categories")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance adminCategoriesPage() {
        return adminCategories.data("categories", categoryService.getAllCategories());
    }
}
