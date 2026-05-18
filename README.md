# 📱 Phone Store - Modern E-Commerce Platform

[![Quarkus](https://img.shields.io/badge/Framework-Quarkus-333333.svg?style=flat&logo=quarkus)](https://quarkus.io/)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg?style=flat&logo=openjdk)](https://openjdk.org/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1.svg?style=flat&logo=mysql)](https://www.mysql.com/)
[![Hibernate](https://img.shields.io/badge/ORM-Hibernate-59666C.svg?style=flat&logo=hibernate)](https://hibernate.org/)

Nền tảng thương mại điện tử hiện đại được xây dựng bằng **Quarkus (Supersonic Subatomic Java)**, với giao diện người dùng đáp ứng và API RESTful mạnh mẽ. Dự án này thể hiện các thực tiễn phát triển web hiện đại bao gồm dịch vụ phản ứng, kết xuất phía máy chủ với Qute, và quản lý cơ sở dữ liệu nâng cao.

---

## 🚀 Tính năng chính

### 🛒 Trải nghiệm mua sắm
- **📂 Danh mục sản phẩm**: Duyệt điện thoại với bộ lọc nâng cao (danh mục, giá, đánh giá)
- **🔍 Tìm kiếm thông minh**: Tìm kiếm sản phẩm theo từ khóa
- **⭐ Sản phẩm nổi bật**: Hiển thị sản phẩm được đề xuất trên trang chủ
- **🛒 Giỏ hàng & Yêu thích**: Lưu sản phẩm qua các phiên làm việc bằng localStorage
- **🎉 Khuyến mãi & Giảm giá**: Áp dụng mã khuyến mãi và xem các chiến dịch tiếp thị
- **💳 Thanh toán mượt mà**: Quy trình thanh toán đa bước với quản lý địa chỉ

### 👤 Quản lý người dùng
- **🔐 Xác thực JWT**: Hệ thống đăng nhập và đăng ký an toàn
- **👤 Quản lý hồ sơ**: Cập nhật thông tin cá nhân và theo dõi hoạt động tài khoản
- **📍 Sổ địa chỉ**: Quản lý nhiều địa chỉ giao hàng với cài đặt mặc định
- **📦 Lịch sử đơn hàng**: Theo dõi trạng thái đơn hàng và xem lịch sử giao dịch chi tiết

### ⚙️ Bảng điều khiển Admin
- **📦 Quản lý sản phẩm**: Các hoạt động CRUD đầy đủ với hỗ trợ tải ảnh
- **📋 Xử lý đơn hàng**: Quản lý đơn hàng khách hàng và cập nhật trạng thái vận chuyển
- **👥 Quản lý người dùng**: Giám sát tài khoản người dùng và vai trò
- **🎟️ Quản lý khuyến mãi**: Tạo và quản lý mã tiếp thị và chiết khấu
- **📊 Phân tích**: Tổng quan về hiệu suất cửa hàng và thống kê

---

## 🛠️ Công nghệ sử dụng

| Lớp | Công nghệ |
|-----|-----------|
| **Backend** | Quarkus 3.15, Java 21, RESTeasy Reactive, Jackson |
| **Persistence** | Hibernate ORM with Panache, MySQL 8.0 |
| **Frontend** | Qute Templates, Vanilla JavaScript (ES6+), Bootstrap Icons, Emoji |
| **Tools** | Maven, Lombok, JWT (JSON Web Tokens) |

---

## 🏗️ Bắt đầu

### Yêu cầu
- **JDK 21** hoặc cao hơn
- **MySQL 8.0**
- **Maven 3.9+**

### Thiết lập cơ sở dữ liệu
1. Tạo cơ sở dữ liệu MySQL có tên `phone_store`:
   ```bash
   mysql -u your_user -p -e "CREATE DATABASE phone_store CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
   ```

2. Nhập schema và dữ liệu mẫu:
   ```bash
   mysql -u your_user -p phone_store < DATABASE_SETUP.sql
   ```

3. Cập nhật `src/main/resources/application.properties` với thông tin đăng nhập cơ sở dữ liệu của bạn:
   ```properties
   quarkus.datasource.username=your_user
   quarkus.datasource.password=your_password
   quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/phone_store
   quarkus.datasource.db-kind=mysql
   ```

### Chạy ứng dụng
Chạy ứng dụng ở chế độ phát triển với mã hóa trực tiếp:
```bash
./mvnw quarkus:dev
```
Ứng dụng sẽ có sẵn tại `http://localhost:8080`.

### Xây dựng cho Production
```bash
./mvnw clean package -DskipTests
java -jar target/quarkus-app/quarkus-run.jar
```

---

## 📂 Cấu trúc dự án

```text
Phone Store/
├── src/main/
│   ├── java/org/acme/
│   │   ├── entity/              # JPA Entities (Product, User, Order, etc.)
│   │   ├── repository/          # Panache Repositories
│   │   ├── resource/            # REST API Endpoints
│   │   ├── service/             # Business Logic Layer
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── util/                # Helper classes (JWT, Password, Format)
│   │   ├── PageController.java  # Qute Template Routing
│   │   └── PhoneStoreApplication.java
│   └── resources/
│       ├── application.properties
│       └── templates/
│           ├── layout.html              # Base template
│           ├── index.html               # Trang chủ
│           ├── products.html            # Danh sách sản phẩm
│           ├── product-detail.html      # Chi tiết sản phẩm
│           ├── cart.html                # Giỏ hàng
│           ├── checkout.html            # Thanh toán
│           ├── orders.html              # Lịch sử đơn hàng
│           ├── order-detail.html        # Chi tiết đơn hàng
│           ├── addresses.html           # Sổ địa chỉ
│           ├── profile.html             # Hồ sơ cá nhân
│           ├── promotions.html          # Khuyến mãi
│           ├── wishlist.html            # Danh sách yêu thích
│           ├── login.html               # Đăng nhập
│           ├── register.html            # Đăng ký
│           ├── forgot-password.html     # Quên mật khẩu
│           └── admin/                   # Admin templates
│               ├── admin-layout.html
│               ├── admin-dashboard.html
│               ├── products.html
│               ├── orders.html
│               ├── users.html
│               ├── promotions.html
│               └── ...
├── pom.xml                      # Maven configuration
├── DATABASE_SETUP.sql           # Database schema
├── BACKEND_ENDPOINTS.md         # API documentation
├── ICONS_REFERENCE.md           # Icon reference guide
└── README.md                    # This file
```

---

## 📖 Tài liệu API

Thông tin chi tiết về tất cả các endpoint REST và tuyến đường template có thể được tìm thấy trong:
- [📄 BACKEND_ENDPOINTS.md](BACKEND_ENDPOINTS.md) - Tài liệu API đầy đủ
- [📋 ICONS_REFERENCE.md](ICONS_REFERENCE.md) - Hướng dẫn tham khảo icon

---

## 🎯 Các trang chính

### Người dùng
| Trang | URL | Mô tả |
|-------|-----|-------|
| Trang chủ | `/` | Hiển thị danh mục và sản phẩm nổi bật |
| Danh sách sản phẩm | `/products` | Duyệt tất cả sản phẩm với bộ lọc |
| Chi tiết sản phẩm | `/products/{id}` | Xem thông tin chi tiết sản phẩm |
| Giỏ hàng | `/cart` | Quản lý giỏ hàng |
| Thanh toán | `/checkout` | Quy trình thanh toán |
| Đơn hàng | `/orders` | Xem lịch sử đơn hàng |
| Hồ sơ | `/profile` | Quản lý thông tin cá nhân |
| Địa chỉ | `/addresses` | Quản lý địa chỉ giao hàng |
| Khuyến mãi | `/promotions` | Xem các ưu đãi hiện tại |
| Yêu thích | `/wishlist` | Danh sách sản phẩm yêu thích |
| Đăng nhập | `/login` | Đăng nhập tài khoản |
| Đăng ký | `/register` | Tạo tài khoản mới |

### Admin
| Trang | URL | Mô tả |
|-------|-----|-------|
| Bảng điều khiển | `/admin/dashboard` | Tổng quan thống kê |
| Sản phẩm | `/admin/products` | Quản lý sản phẩm |
| Đơn hàng | `/admin/orders` | Quản lý đơn hàng |
| Người dùng | `/admin/users` | Quản lý người dùng |
| Khuyến mãi | `/admin/promotions` | Quản lý khuyến mãi |

---

## 🔐 Bảo mật

- **JWT Authentication**: Tất cả các endpoint API được bảo vệ bằng JWT tokens
- **Password Hashing**: Mật khẩu được mã hóa bằng bcrypt
- **CORS**: Cấu hình CORS để bảo vệ chống lại các cuộc tấn công
- **SQL Injection Prevention**: Sử dụng Hibernate ORM để ngăn chặn SQL injection

---

## 📝 Ghi chú phát triển

### Qute Templates
- Tất cả template HTML sử dụng Qute template engine
- JavaScript được viết trong `<script>` tags để tránh xung đột với Qute syntax
- Sử dụng `fetch()` API để gọi REST endpoints

### API Calls
- Tất cả dữ liệu động được tải qua JavaScript fetch API
- Token JWT được lưu trữ trong localStorage
- Xử lý lỗi và loading states được triển khai cho tất cả các yêu cầu

### Styling
- Sử dụng CSS Grid và Flexbox cho layout responsive
- Bootstrap Icons được sử dụng cho các biểu tượng
- Emoji được sử dụng cho các biểu tượng trang trí

---

## 🐛 Troubleshooting

### Lỗi kết nối cơ sở dữ liệu
```
Kiểm tra:
1. MySQL service đang chạy
2. Thông tin đăng nhập trong application.properties
3. Cơ sở dữ liệu phone_store tồn tại
```

### Lỗi Qute Template
```
Kiểm tra:
1. Cú pháp template đúng
2. Không sử dụng arrow functions trong template
3. Tất cả biến được truyền từ controller
```

### Lỗi CORS
```
Kiểm tra:
1. Cấu hình CORS trong application.properties
2. Request headers đúng
3. Origin được phép
```

---

## 📜 Giấy phép
Dự án này dành cho mục đích giáo dục. Tất cả quyền được bảo lưu.

---

## 👨‍💻 Đóng góp
Chúng tôi hoan nghênh các đóng góp! Vui lòng tạo pull request với các cải tiến của bạn.

---

*Xây dựng với ❤️ sử dụng Quarkus và Java 21.*
