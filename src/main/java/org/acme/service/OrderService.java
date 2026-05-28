package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.CreateOrderRequestDTO;
import org.acme.dto.OrderDTO;
import org.acme.dto.OrderItemDTO;
import org.acme.dto.ProductDTO;
import org.acme.entity.Address;
import org.acme.entity.Order;
import org.acme.entity.OrderItem;
import org.acme.entity.Product;
import org.acme.entity.User;
import org.acme.repository.AddressRepository;
import org.acme.repository.OrderItemRepository;
import org.acme.repository.OrderRepository;
import org.acme.repository.ProductRepository;
import org.acme.repository.PromotionRepository;
import org.acme.repository.UserRepository;
import org.acme.entity.Promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderService {

    @Inject
    UserRepository userRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    AddressRepository addressRepository;

    @Inject
    PromotionRepository promotionRepository;

    // ==========================================
    // CÁC PHƯƠNG THỨC MỚI BỔ SUNG
    // ==========================================

    @Transactional
    public OrderDTO createOrder(CreateOrderRequestDTO request) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        
        if (request.getAddressId() != null) {
            Address address = addressRepository.findById(request.getAddressId());
            if (address != null) {
                order.setAddressId(request.getAddressId());
                order.setCustomerName(address.getFullName());
                order.setCustomerPhone(address.getPhone());
                
                String fullAddress = address.getAddress() + ", " + 
                                     address.getWard() + ", " + 
                                     address.getDistrict() + ", " + 
                                     address.getCity();
                order.setShippingAddress(fullAddress);
            }
        }
        order.setPaymentMethod(request.getPaymentMethod());
        order.setNotes(request.getNotes());
        
        orderRepository.persist(order);

        for (CreateOrderRequestDTO.OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId());
            if (product == null) {
                throw new NotFoundException("Sản phẩm không tồn tại với ID: " + itemReq.getProductId());
            }

            if (product.getStock() < itemReq.getQuantity()) {
                throw new BadRequestException("Sản phẩm '" + product.getName() + "' không đủ hàng! Chỉ còn " + product.getStock() + " chiếc.");
            }

            // Trừ tồn kho
            product.setStock(product.getStock() - itemReq.getQuantity());

            // Tính tiền
            BigDecimal priceAsBigDecimal = BigDecimal.valueOf(product.getPrice());
            BigDecimal itemQuantity = new BigDecimal(itemReq.getQuantity());
            BigDecimal itemTotal = priceAsBigDecimal.multiply(itemQuantity);

            totalAmount = totalAmount.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.id); // Lấy id của Order vừa tạo
            orderItem.setProductId(product.id);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(product.getPrice()); // Lưu giá sản phẩm tại thời điểm mua

            orderItemRepository.persist(orderItem);
        }

        BigDecimal discountAmount = BigDecimal.ZERO;
        if (request.getPromotionCode() != null && !request.getPromotionCode().trim().isEmpty()) {
            Promotion promo = promotionRepository.find("code", request.getPromotionCode().trim()).firstResult();
            if (promo == null) {
                throw new BadRequestException("Mã khuyến mãi không hợp lệ");
            }
            if (promo.getExpiryDate() != null && promo.getExpiryDate().isBefore(java.time.LocalDate.now())) {
                throw new BadRequestException("Mã khuyến mãi đã hết hạn");
            }
            
            // Assuming discountValue > 100 means direct amount, otherwise percentage
            if (promo.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
                discountAmount = promo.getDiscountValue();
            } else {
                // percentage
                discountAmount = totalAmount.multiply(promo.getDiscountValue()).divide(new BigDecimal("100"));
            }
            
            // check min order value? Not in Promotion.java, skipping.
            if (discountAmount.compareTo(totalAmount) > 0) {
                discountAmount = totalAmount; // Cannot discount more than total
            }
            
            order.setPromotionCode(promo.getCode());
            order.setDiscountAmount(discountAmount);
        }
        
        order.setTotalAmount(totalAmount.subtract(discountAmount));
        return mapToOrderDTO(order);
    }

    @Transactional
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            return null;
        }
        return mapToOrderDTO(order);
    }

    @Transactional
    public List<OrderDTO> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.find("userId", userId).list();
        return orders.stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NotFoundException("Không tìm thấy đơn hàng với ID: " + orderId);
        }
        order.setStatus(newStatus);
        return mapToOrderDTO(order);
    }

    // ==========================================
    // CÁC PHƯƠNG THỨC CŨ
    // ==========================================

    @Transactional
    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.find("orderId", orderId).list();
        return orderItems.stream()
                .map(this::mapToOrderItemDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDTO> getAllOrders(){
        List<Order> orders = orderRepository.listAll();
        return orders.stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO mapToOrderDTO(Order order) {
        if (order == null) return null;
        
        List<OrderItem> orderItems = orderItemRepository.find("orderId", order.id).list();
        List<OrderItemDTO> itemDTOs = orderItems.stream()
                .map(this::mapToOrderItemDTO)
                .collect(Collectors.toList());
                
        String customerName = order.getCustomerName();
        String customerPhone = order.getCustomerPhone();
        String shippingAddress = order.getShippingAddress();
        
        // Fallback for seed/older orders where customer info is not saved directly in Order
        if ((customerName == null || customerName.isEmpty()) || 
            (customerPhone == null || customerPhone.isEmpty()) || 
            (shippingAddress == null || shippingAddress.isEmpty())) {
            
            if (order.getUserId() != null) {
                User user = userRepository.findById(order.getUserId());
                if (user != null) {
                    if (customerName == null || customerName.isEmpty()) {
                        customerName = user.getFullName();
                    }
                    if (customerPhone == null || customerPhone.isEmpty()) {
                        customerPhone = user.getPhone();
                    }
                }
                
                if (shippingAddress == null || shippingAddress.isEmpty()) {
                    // Try to find default address or first address of the user
                    List<Address> addresses = addressRepository.find("user.id", order.getUserId()).list();
                    if (!addresses.isEmpty()) {
                        Address addr = addresses.stream()
                                .filter(Address::getIsDefault)
                                .findFirst()
                                .orElse(addresses.get(0));
                                
                        shippingAddress = addr.getAddress() + ", " + 
                                          addr.getWard() + ", " + 
                                          addr.getDistrict() + ", " + 
                                          addr.getCity();
                        
                        // Also use address fullName/phone if user's own details are null
                        if (customerName == null || customerName.isEmpty()) {
                            customerName = addr.getFullName();
                        }
                        if (customerPhone == null || customerPhone.isEmpty()) {
                            customerPhone = addr.getPhone();
                        }
                    }
                }
            }
        }
        
        if (customerName == null || customerName.isEmpty()) {
            customerName = "Khách hàng #" + (order.getUserId() != null ? order.getUserId() : "N/A");
        }
        if (customerPhone == null || customerPhone.isEmpty()) {
            customerPhone = "N/A";
        }
        if (shippingAddress == null || shippingAddress.isEmpty()) {
            shippingAddress = "Chưa cập nhật địa chỉ";
        }
        
        String notes = order.getNotes();
        String paymentMethod = order.getPaymentMethod();
        if (paymentMethod == null) {
            paymentMethod = "cod"; // Default to cash on delivery
        }
                
        return new OrderDTO(
                order.id,
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getAddressId(),
                paymentMethod,
                notes,
                notes, // note alias
                shippingAddress,
                customerName,
                customerPhone,
                order.getDiscountAmount(),
                order.getPromotionCode(),
                itemDTOs
        );
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem item) {
        if (item == null) return null;
        Product product = productRepository.findById(item.getProductId());
        ProductDTO productDTO = null;
        String productName = "Sản phẩm không tồn tại";
        String productImage = "/images/placeholder.png";
        Double price = 0.0;
        
        if (product != null) {
            productName = product.getName();
            productImage = product.getImageUrl();
            price = product.getPrice();
            productDTO = new ProductDTO(
                product.id,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getOriginalPrice(),
                product.getDiscount(),
                product.getImageUrl(),
                product.getStock(),
                product.getBrand(),
                product.getRating(),
                product.getReviewCount(),
                product.getCategory() != null ? product.getCategory().id : null,
                product.getSpecifications() != null ? product.getSpecifications().toString() : null,
                product.getWarranty(),
                product.getFeatured()
            );
        }
        
        Double itemPrice = item.getPrice() != null ? item.getPrice() : price;
        
        return new OrderItemDTO(
                item.id,
                item.getOrderId(),
                item.getProductId(),
                item.getQuantity(),
                itemPrice,
                productName,
                productImage,
                productDTO
        );
    }
}