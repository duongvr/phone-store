package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.ReviewDTO;
import org.acme.entity.Review;
import org.acme.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReviewService {

    @Inject
    ReviewRepository reviewRepository;

    @Transactional
    public List<ReviewDTO> getReviewsByProduct(Long productId) {
        List<Review> reviews = reviewRepository.find("productId", productId).list();

        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO addReview(ReviewDTO dto) {
        Review review = new Review();
        review.setProductId(dto.getProductId());
        review.setUserId(dto.getUserId());
        review.setRating(dto.getRating());
        review.setContent(dto.getContent());

        // Tự động gán thời gian hiện tại lúc tạo đánh giá
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.persist(review);

        return mapToDTO(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        boolean deleted = reviewRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Không tìm thấy đánh giá với ID: " + id);
        }
    }

    private ReviewDTO mapToDTO(Review review) {
        if (review == null) return null;

        return new ReviewDTO(
                review.id,
                review.getProductId(),
                review.getUserId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}