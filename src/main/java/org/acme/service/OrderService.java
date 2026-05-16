package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.CreateOrderRequestDTO;
import org.acme.dto.OrderDTO;
import org.acme.dto.OrderItemDTO;
import org.acme.entity.Order;
import org.acme.entity.OrderItem;
import org.acme.entity.Product;
import org.acme.repository.OrderItemRepository;
import org.acme.repository.OrderRepository;
import org.acme.repository.ProductRepository;
import org.acme.repository.UserRepository;

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

            orderItemRepository.persist(orderItem);
        }

        order.setTotalAmount(totalAmount);
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
        return new OrderDTO(
                order.id,
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem item) {
        if (item == null) return null;
        return new OrderItemDTO(
                item.id,
                item.getOrderId(),
                item.getProductId(),
                item.getQuantity()
        );
    }
}