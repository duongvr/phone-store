package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private Long addressId;
    private String paymentMethod;
    private String notes;
    private String note;
    private String shippingAddress;
    private String customerName;
    private String customerPhone;
    private List<OrderItemDTO> items;
}