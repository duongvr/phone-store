-- =========================
-- TẠO DATABASE
-- =========================
CREATE DATABASE PhoneShopDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE PhoneShopDB;

-- =========================
-- TẠO BẢNG
-- =========================

-- 1. Users
CREATE TABLE Users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100),
  role VARCHAR(20) DEFAULT 'USER'
);

-- 2. Categories
CREATE TABLE Categories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  description TEXT
);

-- 3. Products
CREATE TABLE Products (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  price DECIMAL CHECK (price > 0),
  stock INT CHECK (stock >= 0),
  category_id BIGINT,
  image_url VARCHAR(255),
  FOREIGN KEY (category_id) REFERENCES Categories(id)
);

-- 4. Addresses
CREATE TABLE Addresses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  address_detail VARCHAR(255),
  is_default BOOLEAN,
  FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- 5. Orders
CREATE TABLE Orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  total_amount DECIMAL,
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- 6. Order_Items
CREATE TABLE Order_Items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT,
  product_id BIGINT,
  quantity INT CHECK (quantity > 0),
  FOREIGN KEY (order_id) REFERENCES Orders(id),
  FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- 7. Reviews
CREATE TABLE Reviews (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT,
  user_id BIGINT,
  rating INT CHECK (rating BETWEEN 1 AND 5),
  content TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (product_id) REFERENCES Products(id),
  FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- 8. Promotions
CREATE TABLE Promotions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) UNIQUE,
  discount_value DECIMAL,
  expiry_date DATE
);

-- =========================
-- CHÈN DỮ LIỆU MẪU
-- =========================

-- Users
INSERT INTO Users (email, password, full_name, role) VALUES
('nguyen.an@gmail.com', 'hashed_password1', 'Nguyen Van An', 'USER'),
('tran.binh@gmail.com', 'hashed_password2', 'Tran Thi Binh', 'USER'),
('admin@shop.com', 'hashed_admin', 'Admin System', 'ADMIN'),
('le.hoa@gmail.com', 'hashed_password3', 'Le Thi Hoa', 'USER'),
('pham.tuan@gmail.com', 'hashed_password4', 'Pham Tuan', 'USER');

-- Categories
INSERT INTO Categories (name, description) VALUES
('iPhone', 'Các dòng điện thoại Apple iPhone'),
('Samsung', 'Các dòng điện thoại Samsung Galaxy'),
('Xiaomi', 'Các dòng điện thoại Xiaomi'),
('Oppo', 'Các dòng điện thoại Oppo'),
('Realme', 'Các dòng điện thoại Realme');

-- Products (20 sản phẩm)
INSERT INTO Products (name, price, stock, category_id, image_url) VALUES
('iPhone 15 Pro Max', 33990000, 50, 1, '...'),
('iPhone 15', 22990000, 60, 1, '...'),
('iPhone 14 Pro', 25990000, 40, 1, '...'),
('iPhone 13', 18990000, 70, 1, '...'),
('iPhone SE 2022', 10990000, 30, 1, '...'),

('Samsung Galaxy S24 Ultra', 28990000, 40, 2, '...'),
('Samsung Galaxy S24', 23990000, 50, 2, '...'),
('Samsung Galaxy Z Fold5', 44990000, 20, 2, '...'),
('Samsung Galaxy Z Flip5', 25990000, 25, 2, '...'),
('Samsung Galaxy A54', 9990000, 100, 2, '...'),

('Xiaomi 14 Ultra', 22990000, 30, 3, '...'),
('Xiaomi 14', 18990000, 40, 3, '...'),
('Xiaomi Redmi Note 13 Pro', 7990000, 120, 3, '...'),
('Xiaomi Redmi 12', 4990000, 150, 3, '...'),
('Xiaomi Poco F5', 10990000, 60, 3, '...'),

('Oppo Find X6 Pro', 24990000, 35, 4, '...'),
('Oppo Reno10 Pro', 13990000, 50, 4, '...'),
('Oppo A78', 6990000, 80, 4, '...'),

('Realme GT Neo 5', 12990000, 45, 5, '...'),
('Realme C55', 4990000, 90, 5, '...');

-- Addresses
INSERT INTO Addresses (user_id, address_detail, is_default) VALUES
(1, '123 Nguyen Trai, Ha Noi', TRUE),
(2, '45 Le Loi, Ho Chi Minh City', TRUE),
(3, '12 Tran Hung Dao, Da Nang', TRUE),
(4, '88 Nguyen Hue, Hue', FALSE),
(5, '56 Bach Dang, Hai Phong', TRUE);

-- Orders
INSERT INTO Orders (user_id, total_amount, status) VALUES
(1, 33990000, 'PENDING'),
(2, 28990000, 'CONFIRMED'),
(3, 22990000, 'SHIPPED'),
(4, 10990000, 'DELIVERED'),
(5, 4990000, 'CANCELLED');

-- Order_Items
INSERT INTO Order_Items (order_id, product_id, quantity) VALUES
(1, 1, 1),
(2, 6, 1),
(3, 11, 1),
(4, 5, 2),
(5, 20, 1);

-- Reviews
INSERT INTO Reviews (product_id, user_id, rating, content) VALUES
(1, 1, 5, 'Máy chạy rất mượt, camera đẹp.'),
(2, 2, 4, 'Màn hình sắc nét, pin tốt nhưng hơi nặng.'),
(3, 3, 5, 'Thiết kế sang trọng, hiệu năng mạnh.'),
(4, 4, 3, 'Giá hơi cao so với cấu hình.'),
(5, 5, 4, 'Máy nhỏ gọn, tiện lợi.');

-- Promotions
INSERT INTO Promotions (code, discount_value, expiry_date) VALUES
('SALE10', 10.00, '2026-12-31'),
('NEWUSER50', 50.00, '2026-06-30'),
('IPHONE20', 20.00, '2026-09-30'),
('SAMSUNG15', 15.00, '2026-08-31'),
('XIAOMI5', 5.00, '2026-07-31');


