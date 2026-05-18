package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class News extends PanacheEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "summary", length = 500)
    private String summary;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "author")
    private String author;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "category")
    private String category;
}
