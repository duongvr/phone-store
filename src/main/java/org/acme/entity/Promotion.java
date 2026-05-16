package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promotions")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Promotion extends PanacheEntity {

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 0)
    private BigDecimal discountValue;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
}
