-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: phoneshopdb
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `address_detail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `city` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` bigint DEFAULT NULL,
  `district` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `postalCode` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` bigint DEFAULT NULL,
  `ward` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,1,'123 Nguyen Trai, Ha Noi',1,'','',NULL,'','','','',NULL,NULL,''),(2,2,'45 Le Loi, Ho Chi Minh City',1,'','',NULL,'','','','',NULL,NULL,''),(3,3,'12 Tran Hung Dao, Da Nang',1,'','',NULL,'','','','',NULL,NULL,''),(4,4,'88 Nguyen Hue, Hue',0,'','',NULL,'','','','',NULL,NULL,''),(5,5,'56 Bach Dang, Hai Phong',1,'','',NULL,'','','','',NULL,NULL,'');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `addresses_seq`
--

DROP TABLE IF EXISTS `addresses_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses_seq`
--

LOCK TABLES `addresses_seq` WRITE;
/*!40000 ALTER TABLE `addresses_seq` DISABLE KEYS */;
INSERT INTO `addresses_seq` VALUES (1);
/*!40000 ALTER TABLE `addresses_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `created_at` bigint DEFAULT NULL,
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'iPhone','Các dòng điện thoại Apple iPhone',NULL,NULL,NULL),(2,'Samsung','Các dòng điện thoại Samsung Galaxy',NULL,NULL,NULL),(3,'Xiaomi','Các dòng điện thoại Xiaomi',NULL,NULL,NULL),(4,'Oppo','Các dòng điện thoại Oppo',NULL,NULL,NULL),(5,'Realme','Các dòng điện thoại Realme',NULL,NULL,NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories_seq`
--

DROP TABLE IF EXISTS `categories_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories_seq`
--

LOCK TABLES `categories_seq` WRITE;
/*!40000 ALTER TABLE `categories_seq` DISABLE KEYS */;
INSERT INTO `categories_seq` VALUES (1);
/*!40000 ALTER TABLE `categories_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `order_items_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,1),(2,2,6,1),(3,3,11,1),(4,4,5,2),(5,5,20,1);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items_seq`
--

DROP TABLE IF EXISTS `order_items_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items_seq`
--

LOCK TABLES `order_items_seq` WRITE;
/*!40000 ALTER TABLE `order_items_seq` DISABLE KEYS */;
INSERT INTO `order_items_seq` VALUES (1);
/*!40000 ALTER TABLE `order_items_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `total_amount` decimal(10,0) DEFAULT NULL,
  `status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,33990000,'PENDING','2026-05-13 09:50:35'),(2,2,28990000,'CONFIRMED','2026-05-13 09:50:35'),(3,3,22990000,'SHIPPED','2026-05-13 09:50:35'),(4,4,10990000,'DELIVERED','2026-05-13 09:50:35'),(5,5,4990000,'CANCELLED','2026-05-13 09:50:35');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_seq`
--

DROP TABLE IF EXISTS `orders_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_seq`
--

LOCK TABLES `orders_seq` WRITE;
/*!40000 ALTER TABLE `orders_seq` DISABLE KEYS */;
INSERT INTO `orders_seq` VALUES (1);
/*!40000 ALTER TABLE `orders_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `stock` int DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `brand` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` bigint DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `discount` int DEFAULT NULL,
  `featured` bit(1) NOT NULL,
  `original_price` double DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  `specifications` json DEFAULT NULL,
  `updated_at` bigint DEFAULT NULL,
  `warranty` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_chk_1` CHECK ((`price` > 0)),
  CONSTRAINT `products_chk_2` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'iPhone 15 Pro Max',33990000,50,1,'https://images.tokopedia.net/img/cache/200-square/VqbcmM/2023/10/18/ed199db4-768f-4ebe-8331-95dde8b86247.jpg',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(2,'iPhone 15',22990000,60,1,'https://th.bing.com/th/id/OIP.56YLv4-U4VVb7fF8IG5ILQHaEK?r=0&o=7rm=3&rs=1&pid=ImgDetMain&o=7&rm=3',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(3,'iPhone 14 Pro',25990000,40,1,'https://tse1.explicit.bing.net/th/id/OIP.QKrGyCN-t6wsb48hPe-5VwHaE7?r=0&rs=1&pid=ImgDetMain&o=7&rm=3',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(4,'iPhone 13',18990000,70,1,'https://th.bing.com/th/id/OIP.Kt-I8ARYzmGPyHUiDiHt9gAAAA?w=312&h=200&c=12&rs=1&o=6&dpr=1.7&pid=AlgoBlockDebug',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(5,'iPhone SE 2022',10990000,30,1,'https://th.bing.com/th/id/OIP._kNFtwWpxgKFXM1E401U8gHaHa?w=106&h=108&c=7&qlt=90&bgcl=1e7934&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(6,'Samsung Galaxy S24 Ultra',28990000,40,2,'https://th.bing.com/th/id/OIP.K8bU0kx7sfU0UiOaZf4lfAHaDx?w=210&h=108&c=7&qlt=90&bgcl=85b437&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(7,'Samsung Galaxy S24',23990000,50,2,'https://image-us.samsung.com/us/smartphones/galaxy-s24/all-gallery/01_E3_TitaniumBlack_Lockup_1600x1200.jpg?$product-details-jpg$?$product-details-thumbnail-jpg$',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(8,'Samsung Galaxy Z Fold5',44990000,20,2,'https://th.bing.com/th/id/OIP.EI1ZOn_qZQwGnosDO8dQAQHaE7?w=131&h=108&c=7&qlt=90&bgcl=c22ae2&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(9,'Samsung Galaxy Z Flip5',25990000,25,2,'https://th.bing.com/th/id/OIP.ZDVL5sB_3Lgo7N9dJ20FEAHaHa?w=87&h=108&c=7&qlt=90&bgcl=a87142&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(10,'Samsung Galaxy A54',9990000,100,2,'https://th.bing.com/th/id/OIP.LyqTE-BmxaIsyqAISqnZlgHaEI?w=174&h=108&c=7&qlt=90&bgcl=99edfc&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(11,'Xiaomi 14 Ultra',22990000,30,3,'https://th.bing.com/th/id/OIP.sfGLf78lSP4CGj5crCxdmgHaHa?w=101&h=108&c=7&qlt=90&bgcl=ff9e17&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(12,'Xiaomi 14',18990000,40,3,'https://th.bing.com/th/id/OIP.sH0_GbAM8u9Gu2EsZoc5jAHaED?w=168&h=108&c=7&qlt=90&bgcl=bec0a8&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(13,'Xiaomi Redmi Note 13 Pro',7990000,120,3,'https://th.bing.com/th/id/OIP.YwUQEIYyqd6veQRGKMqGTAHaFj?w=134&h=108&c=7&qlt=90&bgcl=bd450e&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(14,'Xiaomi Redmi 12',4990000,150,3,'https://th.bing.com/th/id/OIP.WhZ-0O0XdpHbrpLj4fzB9gHaF1?w=115&h=108&c=7&qlt=90&bgcl=983eb5&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(15,'Xiaomi Poco F5',10990000,60,3,'https://th.bing.com/th/id/OIP.kOhTgZnBVgxoJWlMnn1JWQHaEK?w=159&h=108&c=7&qlt=90&bgcl=6645f8&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(16,'Oppo Find X6 Pro',24990000,35,4,'https://th.bing.com/th/id/OIP.W_rVYl4mz8IvKZBQ88tW9wHaHa?w=128&h=108&c=7&qlt=90&bgcl=fc491d&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(17,'Oppo Reno10 Pro',13990000,50,4,'https://th.bing.com/th/id/OIP.GJiv--1P_aR443Hf_TMWvgHaHa?w=101&h=108&c=7&qlt=90&bgcl=2e7a4f&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(18,'Oppo A78',6990000,80,4,'https://th.bing.com/th/id/OIP._qSi2oJc6W7XffIOAaXcAAAAAA?w=312&h=200&c=12&rs=1&o=6&dpr=1.7&pid=AlgoBlockDebug',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(19,'Realme GT Neo 5',12990000,45,5,'https://th.bing.com/th/id/OIP.Nj98Jg6uf7QYvtBcORHMxwHaEK?w=184&h=108&c=7&qlt=90&bgcl=20a259&r=0&o=6&dpr=1.7&pid=13.1',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL),(20,'Realme C55',4990000,90,5,'https://th.bing.com/th/id/OIP.jJrT-DqeRaz0SkkfZkg5bAHaHa?w=312&h=200&c=12&rs=1&o=6&dpr=1.7&pid=AlgoBlockDebug',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_seq`
--

DROP TABLE IF EXISTS `products_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_seq`
--

LOCK TABLES `products_seq` WRITE;
/*!40000 ALTER TABLE `products_seq` DISABLE KEYS */;
INSERT INTO `products_seq` VALUES (1);
/*!40000 ALTER TABLE `products_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discount_value` decimal(10,0) DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
INSERT INTO `promotions` VALUES (1,'SALE10',10,'2026-12-31'),(2,'NEWUSER50',50,'2026-06-30'),(3,'IPHONE20',20,'2026-09-30'),(4,'SAMSUNG15',15,'2026-08-31'),(5,'XIAOMI5',5,'2026-07-31');
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions_seq`
--

DROP TABLE IF EXISTS `promotions_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions_seq`
--

LOCK TABLES `promotions_seq` WRITE;
/*!40000 ALTER TABLE `promotions_seq` DISABLE KEYS */;
INSERT INTO `promotions_seq` VALUES (1);
/*!40000 ALTER TABLE `promotions_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `reviews_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,1,1,5,'Máy chạy rất mượt, camera đẹp.','2026-05-13 09:50:35'),(2,2,2,4,'Màn hình sắc nét, pin tốt nhưng hơi nặng.','2026-05-13 09:50:35'),(3,3,3,5,'Thiết kế sang trọng, hiệu năng mạnh.','2026-05-13 09:50:35'),(4,4,4,3,'Giá hơi cao so với cấu hình.','2026-05-13 09:50:35'),(5,5,5,4,'Máy nhỏ gọn, tiện lợi.','2026-05-13 09:50:35');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews_seq`
--

DROP TABLE IF EXISTS `reviews_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews_seq`
--

LOCK TABLES `reviews_seq` WRITE;
/*!40000 ALTER TABLE `reviews_seq` DISABLE KEYS */;
INSERT INTO `reviews_seq` VALUES (1);
/*!40000 ALTER TABLE `reviews_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'USER',
  `active` bit(1) NOT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` bigint DEFAULT NULL,
  `fullName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nguyen.an@gmail.com','hashed_password1','Nguyen Van An','USER',_binary '\0',NULL,NULL,'',NULL,NULL),(2,'tran.binh@gmail.com','hashed_password2','Tran Thi Binh','USER',_binary '\0',NULL,NULL,'',NULL,NULL),(3,'admin@shop.com','hashed_admin','Admin System','ADMIN',_binary '\0',NULL,NULL,'',NULL,NULL),(4,'le.hoa@gmail.com','hashed_password3','Le Thi Hoa','USER',_binary '\0',NULL,NULL,'',NULL,NULL),(5,'pham.tuan@gmail.com','hashed_password4','Pham Tuan','USER',_binary '\0',NULL,NULL,'',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_seq`
--

DROP TABLE IF EXISTS `users_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_seq`
--

LOCK TABLES `users_seq` WRITE;
/*!40000 ALTER TABLE `users_seq` DISABLE KEYS */;
INSERT INTO `users_seq` VALUES (1);
/*!40000 ALTER TABLE `users_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-13 17:05:24
