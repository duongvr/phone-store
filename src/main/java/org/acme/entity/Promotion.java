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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 0)
    private BigDecimal discountValue;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "banner")
    private String banner;

    @Column(name = "badge")
    private String badge;

    @Column(name = "conditions", length = 500)
    private String conditions;

    @Column(name = "category_id")
    private Long categoryId;
}
