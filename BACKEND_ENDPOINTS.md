# Backend Endpoints cần implement

## PageController (trả về Qute template)

| Route | Method | Template | Dữ liệu cần truyền vào template |
|-------|--------|----------|----------------------------------|
| `/` | GET | `index.html` | `categories: List<Category>`, `featuredProducts: List<Product>` (featured=true) |
| `/products` | GET | `products.html` | `categories: List<Category>`, `products: List<Product>` (có filter: category, price, rating, sort) |
| `/products/{id}` | GET | `product-detail.html` | `product: Product` (kèm category, đảm bảo stock/rating/discount không null) |
| `/cart` | GET | `cart.html` | `cartItems: List<CartItem>`, `subtotal: Double`, `shippingFee: Double`, `discount: Double` (default 0), `total: Double` |
| `/checkout` | GET | `checkout.html` | `selectedAddress: Address`, `orderItems: List<CartItem>`, `subtotal`, `shippingFee`, `discount` (default 0), `total` |
| `/orders` | GET | `orders.html` | `orders: List<Order>` (kèm items, statusLabel, shippingAddress, paymentMethod) |
| `/addresses` | GET | `addresses.html` | `addresses: List<Address>` |
| `/profile` | GET | `profile.html` | `user: User` |
| `/promotions` | GET | `promotions.html` | `promotions: List<Promotion>` (kèm banner, badge, code, conditions, endDate, categoryId) |
| `/login` | GET | `login.html` | _(không cần data)_ |
| `/register` | GET | `register.html` | _(không cần data)_ |
| `/admin/products` | GET | `admin/products.html` | `products: List<Product>` (admin only) |
| `/admin/orders` | GET | `admin/orders.html` | `orders: List<Order>` (admin only) |
| `/admin/users` | GET | `admin/users.html` | `users: List<User>` (admin only) |
| `/admin/promotions` | GET | `admin/promotions.html` | `promotions: List<Promotion>` (admin only) |
| `/admin/dashboard` | GET | `admin/dashboard.html` | `stats: DashboardStats` (admin only) |

## API REST (trả về JSON, dùng cho JavaScript fetch)

### Auth
| Endpoint | Method | Mô tả | Request Body | Response |
|----------|--------|-------|--------------|----------|
| `/api/users/login` | POST | Đăng nhập, trả về JWT token | `{email, password}` | `{token, userId, fullName, email, role}` |
| `/api/users/register` | POST | Đăng ký tài khoản mới | `{email, password, fullName}` | `{userId, email, fullName, role}` |
| `/api/users/verify-token` | POST | Xác thực token JWT | `{token}` | `{userId, valid}` |
| `/api/users/{userId}` | GET | Lấy thông tin user theo ID | - | `{userId, email, fullName, role, createdAt}` |
| `/api/users/profile` | GET | Lấy thông tin profile user hiện tại | - | `UserDTO` |
| `/api/users/profile` | PUT | Cập nhật thông tin profile | `{fullName, phone, avatar}` | `UserDTO` |
| `/api/users/password` | PUT | Đổi mật khẩu | `{oldPassword, newPassword}` | `{message}` |

### Products
| Endpoint | Method | Mô tả | Query Params | Response |
|----------|--------|-------|--------------|----------|
| `/api/products` | GET | Lấy danh sách sản phẩm | `page=0&pageSize=10` | `List<ProductDTO>` |
| `/api/products/{id}` | GET | Lấy chi tiết 1 sản phẩm | - | `ProductDTO` |
| `/api/products/featured` | GET | Lấy sản phẩm nổi bật | `limit=10` | `List<ProductDTO>` |
| `/api/products/category/{categoryId}` | GET | Lấy sản phẩm theo danh mục | `page=0&pageSize=10` | `List<ProductDTO>` |
| `/api/products/search` | GET | Tìm kiếm sản phẩm | `keyword=...&page=0&pageSize=10` | `List<ProductDTO>` |
| `/api/products` | POST | Tạo sản phẩm mới (admin) | `{name, price, categoryId, stock, imageUrl, description, discount, featured}` | `ProductDTO` |
| `/api/products/{id}` | PUT | Cập nhật sản phẩm (admin) | `{name, price, categoryId, stock, imageUrl, description, discount, featured}` | `ProductDTO` |
| `/api/products/{id}` | DELETE | Xóa sản phẩm (admin) | - | `204 No Content` |
| `/api/products/{id}/stock` | PATCH | Cập nhật số lượng tồn kho | `{quantity}` | `204 No Content` |
| `/api/products/multipart` | POST | Tạo sản phẩm với upload ảnh (admin) | `FormData: {name, price, categoryId, stock, images}` | `ProductDTO` |

### Categories
| Endpoint | Method | Mô tả | Request Body | Response |
|----------|--------|-------|--------------|----------|
| `/api/categories` | GET | Lấy tất cả danh mục | - | `List<CategoryDTO>` |
| `/api/categories/{id}` | GET | Lấy danh mục theo ID | - | `CategoryDTO` |
| `/api/categories` | POST | Tạo danh mục mới (admin) | `{name, icon, description}` | `CategoryDTO` |
| `/api/categories/{id}` | PUT | Cập nhật danh mục (admin) | `{name, icon, description}` | `CategoryDTO` |
| `/api/categories/{id}` | DELETE | Xóa danh mục (admin) | - | `204 No Content` |

### Cart (Local Storage)
| Endpoint | Method | Mô tả | Lưu trữ |
|----------|--------|-------|---------|
| Giỏ hàng | - | Lưu trữ cục bộ trong localStorage | `cart: [{id, name, price, image, quantity}]` |

### Orders
| Endpoint | Method | Mô tả | Request Body | Response |
|----------|--------|-------|--------------|----------|
| `/api/orders` | POST | Tạo đơn hàng mới | `{addressId, paymentMethod, notes, promoCode}` | `OrderDTO` |
| `/api/orders` | GET | Lấy danh sách đơn hàng của user | - | `List<OrderDTO>` |
| `/api/orders/{id}` | GET | Lấy chi tiết đơn hàng | - | `OrderDTO` |
| `/api/orders/{id}/items` | GET | Lấy danh sách items của đơn hàng | - | `List<OrderItemDTO>` |
| `/api/orders/user/{userId}` | GET | Lấy đơn hàng của user theo ID | - | `List<OrderDTO>` |
| `/api/orders/{id}/status` | PATCH | Cập nhật trạng thái đơn hàng | `?status=pending/confirmed/shipped/delivered/cancelled` | `OrderDTO` |

### Addresses
| Endpoint | Method | Mô tả | Request Body | Response |
|----------|--------|-------|--------------|----------|
| `/api/addresses` | GET | Lấy danh sách địa chỉ của user | - | `List<AddressDTO>` |
| `/api/addresses` | POST | Thêm địa chỉ mới | `{fullName, phone, street, ward, district, city, isDefault}` | `AddressDTO` |
| `/api/addresses/{id}` | GET | Lấy chi tiết địa chỉ | - | `AddressDTO` |
| `/api/addresses/{id}` | PUT | Cập nhật địa chỉ | `{fullName, phone, street, ward, district, city, isDefault}` | `AddressDTO` |
| `/api/addresses/{id}` | DELETE | Xóa địa chỉ | - | `204 No Content` |
| `/api/addresses/{id}/default` | PUT | Đặt làm địa chỉ mặc định | - | `AddressDTO` |

### Promotions
| Endpoint | Method | Mô tả | Query Params | Response |
|----------|--------|-------|--------------|----------|
| `/api/promotions` | GET | Lấy danh sách khuyến mãi đang active | `category=...` | `List<PromotionDTO>` |
| `/api/promotions/apply` | POST | Áp dụng mã khuyến mãi | `{code, cartTotal}` | `{discountAmount, message}` |
| `/api/promotions` | POST | Tạo khuyến mãi mới (admin) | `{code, description, discountPercent, maxDiscount, minCartValue, categoryId, startDate, endDate}` | `PromotionDTO` |
| `/api/promotions/{id}` | PUT | Cập nhật khuyến mãi (admin) | `{code, description, discountPercent, maxDiscount, minCartValue, categoryId, startDate, endDate}` | `PromotionDTO` |
| `/api/promotions/{id}` | DELETE | Xóa khuyến mãi (admin) | - | `204 No Content` |

### Reviews
| Endpoint | Method | Mô tả | Request Body | Response |
|----------|--------|-------|--------------|----------|
| `/api/reviews/product/{productId}` | GET | Lấy đánh giá của sản phẩm | - | `List<ReviewDTO>` |
| `/api/reviews` | POST | Tạo đánh giá mới | `{productId, rating, comment}` | `ReviewDTO` |
| `/api/reviews/{id}` | DELETE | Xóa đánh giá | - | `204 No Content` |

### Order Items
| Endpoint | Method | Mô tả | Request Body | Response |
|----------|--------|-------|--------------|----------|
| `/api/order-items` | POST | Thêm item vào đơn hàng | `{orderId, productId, quantity, price}` | `OrderItemDTO` |
| `/api/order-items/{id}` | PUT | Cập nhật item đơn hàng | `{quantity, price}` | `OrderItemDTO` |
| `/api/order-items/{id}` | DELETE | Xóa item khỏi đơn hàng | - | `204 No Content` |

## Lưu ý quan trọng về dữ liệu truyền vào template

Các field sau **phải có giá trị mặc định** (không được null) khi truyền vào template:

| Field | Default | Lý do |
|-------|---------|-------|
| `product.stock` | `0` | So sánh `> 0` trong template |
| `product.rating` | `0.0` | So sánh `> 0` trong template |
| `product.discount` | `0` | So sánh `> 0` trong template |
| `product.reviewCount` | `0` | Hiển thị số đánh giá |
| `discount` (cart/checkout) | `0.0` | So sánh `> 0` trong template |
| `shippingFee` | `0.0` | Hiển thị phí ship |
| `category.icon` | `"📱"` | Hiển thị icon danh mục |
| `order.statusLabel` | `""` | Hiển thị trạng thái đơn hàng |

## Cú pháp sử dụng API trong JavaScript

### Lấy dữ liệu từ API
```javascript
// GET request
const response = await fetch('/api/products');
const data = await response.json();

// GET với query params
const response = await fetch('/api/products?page=0&pageSize=10');
const data = await response.json();

// GET với Authorization header
const token = localStorage.getItem('jwt_token');
const response = await fetch('/api/users/profile', {
  headers: { 'Authorization': 'Bearer ' + token }
});
const data = await response.json();
```

### Gửi dữ liệu đến API
```javascript
// POST request
const response = await fetch('/api/orders', {
  method: 'POST',
  headers: { 
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
  },
  body: JSON.stringify({ addressId: 1, paymentMethod: 'COD' })
});
const data = await response.json();

// PUT request
const response = await fetch('/api/addresses/1', {
  method: 'PUT',
  headers: { 
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
  },
  body: JSON.stringify({ fullName: 'Tên mới', phone: '0123456789' })
});
const data = await response.json();

// DELETE request
const response = await fetch('/api/addresses/1', {
  method: 'DELETE',
  headers: { 'Authorization': 'Bearer ' + token }
});
```

### Xử lý lỗi
```javascript
try {
  const response = await fetch('/api/products');
  if (!response.ok) {
    const error = await response.json();
    console.error('Error:', error.message);
    return;
  }
  const data = await response.json();
  // Xử lý dữ liệu
} catch (error) {
  console.error('Network error:', error);
}
```

## Ghi chú về JavaScript trong HTML

### Tránh lỗi Qute Template Parser
- **KHÔNG** dùng template literals với `${}` trực tiếp trong Qute template
- **KHÔNG** dùng arrow functions `map(item => ...)` trong Qute template
- **KHÔNG** dùng `.replace()` hoặc các method phức tạp trong Qute template

### Cách làm đúng
- Dùng `<script>` tags để viết JavaScript logic
- Dùng `fetch()` để gọi API và lấy dữ liệu
- Dùng `innerHTML` để render HTML động từ dữ liệu API
- Dùng `forEach()` hoặc vòng lặp thay cho `map()` khi render HTML

### Ví dụ đúng
```javascript
// ✅ Đúng: Dùng forEach để render
let html = '';
products.forEach(product => {
  html += `<div>${product.name}</div>`;
});
container.innerHTML = html;

// ❌ Sai: Dùng map trong template
{products.map(p => `<div>${p.name}</div>`)}

// ✅ Đúng: Dùng fetch để lấy dữ liệu
const response = await fetch('/api/products');
const products = await response.json();

// ❌ Sai: Gọi API trong template
{#for product in getProducts()}
```
