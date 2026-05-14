package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.OrderItemDTO;
import org.acme.entity.OrderItem;
import org.acme.repository.OrderItemRepository;

@ApplicationScoped
public class OrderItemService {

    @Inject
    OrderItemRepository orderItemRepository;

    /**
     * Thêm một sản phẩm mới vào đơn hàng
     */
    @Transactional
    public OrderItemDTO addOrderItem(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setOrderId(dto.getOrderId());
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());

        orderItemRepository.persist(item);

        return mapToDTO(item);
    }

    /**
     * Cập nhật số lượng của một OrderItem (khi khách đổi ý)
     */
    @Transactional
    public OrderItemDTO updateQuantity(Long id, int newQuantity) {
        OrderItem item = orderItemRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Không tìm thấy chi tiết đơn hàng với ID: " + id);
        }

        item.setQuantity(newQuantity);
        // Trong Panache, khi ở trong @Transactional, những thay đổi trên Entity
        // sẽ tự động được lưu xuống DB khi kết thúc hàm (Dirty Checking).

        return mapToDTO(item);
    }

    /**
     * Xóa một sản phẩm khỏi đơn hàng
     */
    @Transactional
    public void deleteOrderItem(Long id) {
        boolean deleted = orderItemRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Không tìm thấy chi tiết đơn hàng để xóa.");
        }
    }

    // ==========================================
    // HELPER MAPPING (Entity -> DTO)
    // ==========================================
    private OrderItemDTO mapToDTO(OrderItem item) {
        if (item == null) return null;

        return new OrderItemDTO(
                item.id,
                item.getOrderId(),
                item.getProductId(),
                item.getQuantity()
        );
    }
}