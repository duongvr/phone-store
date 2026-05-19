# 🗺️ API Endpoints — Phone Store

> Base URL: `http://localhost:8080`  
> Tất cả REST API trả về JSON  
> `🔐` = Yêu cầu `Authorization: Bearer <token>` header

---

## 👤 Users — `/api/users`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `POST` | `/api/users/register` | ❌ | Đăng ký tài khoản mới |
| `POST` | `/api/users/login` | ❌ | Đăng nhập, trả về JWT token |
| `POST` | `/api/users/verify-token` | ❌ | Xác thực token, trả về `userId` |
| `POST` | `/api/users/forgot-password` | ❌ | Gửi yêu cầu khôi phục mật khẩu |
| `GET` | `/api/users` | ❌ | Lấy danh sách tất cả user (admin) |
| `GET` | `/api/users/{id}` | ❌ | Lấy thông tin user theo ID |
| `GET` | `/api/users/email/{email}` | ❌ | Lấy user theo email |
| `PUT` | `/api/users/{id}` | ❌ | Cập nhật hồ sơ user |
| `POST` | `/api/users/{id}/change-password` | ❌ | Đổi mật khẩu |
| `POST` | `/api/users/{id}/deactivate` | ❌ | Vô hiệu hóa tài khoản |
| `POST` | `/api/users/{id}/activate` | ❌ | Kích hoạt tài khoản (admin) |

**Request body — Register:**
```json
{ "email": "user@gmail.com", "password": "123456", "fullName": "Nguyễn Văn A" }
```
**Request body — Login:**
```json
{ "email": "user@gmail.com", "password": "123456" }
```
**Response — Login:**
```json
{ "token": "...", "userId": 1, "email": "...", "role": "USER" }
```

---

## 📦 Products — `/api/products`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `GET` | `/api/products` | ❌ | Lấy tất cả sản phẩm (phân trang) |
| `GET` | `/api/products/{id}` | ❌ | Lấy chi tiết sản phẩm theo ID |
| `GET` | `/api/products/featured?limit=10` | ❌ | Lấy sản phẩm nổi bật |
| `GET` | `/api/products/category/{categoryId}` | ❌ | Lấy sản phẩm theo danh mục |
| `GET` | `/api/products/search?keyword=iphone` | ❌ | Tìm kiếm sản phẩm theo từ khóa |
| `POST` | `/api/products` | 🔐 `@Authenticated` | Tạo sản phẩm mới |
| `POST` | `/api/products/multipart` | ❌ | Tạo sản phẩm kèm upload ảnh (multipart) |
| `PUT` | `/api/products/{id}` | ❌ | Cập nhật toàn bộ thông tin sản phẩm |
| `DELETE` | `/api/products/{id}` | ❌ | Xóa sản phẩm |
| `PATCH` | `/api/products/{id}/stock` | ❌ | Cập nhật số lượng tồn kho |

**Query Params:** `?page=0&pageSize=10`

---

## 🗂️ Categories — `/api/categories`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `GET` | `/api/categories` | ❌ | Lấy tất cả danh mục |
| `GET` | `/api/categories/{id}` | ❌ | Lấy danh mục theo ID |
| `POST` | `/api/categories` | ❌ | Tạo danh mục mới (admin) |
| `PUT` | `/api/categories/{id}` | ❌ | Cập nhật danh mục (admin) |
| `DELETE` | `/api/categories/{id}` | ❌ | Xóa danh mục (admin) |

---

## 🛒 Orders — `/api/orders`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `POST` | `/api/orders` | 🔐 JWT | Đặt hàng — `userId` lấy từ token, không tin client |
| `GET` | `/api/orders` | ❌ | Lấy TẤT CẢ đơn hàng (admin) |
| `GET` | `/api/orders/{id}` | ❌ | Lấy chi tiết đơn hàng theo ID |
| `GET` | `/api/orders/user/{userId}` | 🔐 JWT (phải đúng user) | Lấy đơn hàng của **user đang đăng nhập** |
| `GET` | `/api/orders/{orderId}/items` | ❌ | Lấy danh sách item trong đơn hàng |
| `PATCH` | `/api/orders/{id}/status?status=SHIPPED` | ❌ | Cập nhật trạng thái đơn hàng (admin) |

**Request body — POST /api/orders:**
```json
{
  "addressId": 1,
  "paymentMethod": "cod",
  "notes": "Giao giờ hành chính",
  "items": [
    { "productId": 5, "quantity": 2 }
  ]
}
```
> ⚠️ `userId` KHÔNG cần gửi — server tự lấy từ JWT token.

---

## 📍 Addresses — `/api/addresses`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `GET` | `/api/addresses/user/{userId}` | ❌ | Lấy tất cả địa chỉ của user |
| `GET` | `/api/addresses/user/{userId}/default` | ❌ | Lấy địa chỉ mặc định |
| `GET` | `/api/addresses/{id}` | ❌ | Lấy địa chỉ theo ID |
| `POST` | `/api/addresses/user/{userId}` | ❌ | Tạo địa chỉ mới cho user |
| `PUT` | `/api/addresses/{id}` | ❌ | Cập nhật địa chỉ |
| `DELETE` | `/api/addresses/{id}` | ❌ | Xóa địa chỉ |
| `POST` | `/api/addresses/{id}/set-default` | ❌ | Đặt làm địa chỉ mặc định |

**Request body — POST set-default:**
```json
{ "userId": 1 }
```

---

## ⭐ Reviews — `/api/reviews`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `GET` | `/api/reviews/product/{productId}` | ❌ | Lấy đánh giá theo sản phẩm |
| `POST` | `/api/reviews` | ❌ | Thêm đánh giá mới |
| `DELETE` | `/api/reviews/{id}` | ❌ | Xóa đánh giá |

---

## 🎟️ Promotions — `/api/promotions`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `GET` | `/api/promotions` | ❌ | Lấy tất cả khuyến mãi |
| `GET` | `/api/promotions/validate?code=SALE10` | ❌ | Kiểm tra mã khuyến mãi hợp lệ |
| `GET` | `/api/promotions/product/{productId}` | ❌ | Lấy khuyến mãi theo sản phẩm |
| `POST` | `/api/promotions` | ❌ | Tạo khuyến mãi mới (admin) |
| `PUT` | `/api/promotions/{id}` | ❌ | Cập nhật khuyến mãi (admin) |
| `DELETE` | `/api/promotions/{id}` | ❌ | Xóa khuyến mãi (admin) |

---

## 📰 News — `/api/news`

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| `GET` | `/api/news` | ❌ | Lấy tất cả tin tức |
| `GET` | `/api/news/{id}` | ❌ | Lấy tin tức theo ID |
| `POST` | `/api/news` | ❌ | Tạo tin tức mới (admin) |
| `PUT` | `/api/news/{id}` | ❌ | Cập nhật tin tức (admin) |
| `DELETE` | `/api/news/{id}` | ❌ | Xóa tin tức (admin) |

---

## 🖥️ Page Routes (Qute Template) — `PageController`

| Method | URL | Mô tả |
|--------|-----|-------|
| `GET` | `/` | Trang chủ |
| `GET` | `/products` | Danh sách sản phẩm (`?category={id}`) |
| `GET` | `/products/{id}` | Chi tiết sản phẩm |
| `GET` | `/cart` | Giỏ hàng |
| `GET` | `/checkout` | Thanh toán |
| `GET` | `/orders` | Đơn hàng của tôi (yêu cầu login) |
| `GET` | `/orders/{id}` | Chi tiết đơn hàng |
| `GET` | `/profile` | Hồ sơ cá nhân |
| `GET` | `/addresses` | Quản lý địa chỉ |
| `GET` | `/wishlist` | Sản phẩm yêu thích |
| `GET` | `/promotions` | Trang khuyến mãi |
| `GET` | `/news` | Trang tin tức |
| `GET` | `/login` | Trang đăng nhập |
| `GET` | `/register` | Trang đăng ký |
| `GET` | `/admin/dashboard` | Admin — Tổng quan |
| `GET` | `/admin/products` | Admin — Quản lý sản phẩm |
| `GET` | `/admin/products/add` | Admin — Thêm sản phẩm |
| `GET` | `/admin/products/edit/{id}` | Admin — Sửa sản phẩm |
| `GET` | `/admin/orders` | Admin — Quản lý đơn hàng |
| `GET` | `/admin/orders/{id}` | Admin — Chi tiết đơn hàng |
| `GET` | `/admin/users` | Admin — Quản lý người dùng |
| `GET` | `/admin/promotions` | Admin — Quản lý khuyến mãi |
| `GET` | `/admin/categories` | Admin — Quản lý danh mục |

---

## 📊 Trạng Thái Đơn Hàng

| Giá trị | Hiển thị | Ý nghĩa |
|---------|---------|---------|
| `PENDING` | Chờ xác nhận | Đơn hàng vừa đặt |
| `CONFIRMED` | Đã xác nhận | Admin đã duyệt |
| `SHIPPED` | Đang giao | Đang vận chuyển |
| `DELIVERED` | Đã giao | Giao thành công |
| `CANCELLED` | Đã hủy | Đơn bị hủy |
