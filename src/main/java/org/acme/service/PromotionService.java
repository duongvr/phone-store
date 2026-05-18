package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.Promotion;
import org.acme.repository.ProductRepository;
import org.acme.repository.PromotionRepository;

import java.util.List;

@ApplicationScoped
public class PromotionService {
    @Inject
    PromotionRepository promotionRepository;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public List<Promotion> getPromotions() {
        return promotionRepository.listAll();
    }

    @Transactional
    public List<Promotion> getPromotionInProduct(Long productId) {
        // Assuming Promotion has a relationship with Product or we filter by category
        return promotionRepository.list("categoryId", productId); // Placeholder logic
    }

    @Transactional
    public Promotion createPromotion(Promotion promotion) {
        promotionRepository.persist(promotion);
        return promotion;
    }

    @Transactional
    public Promotion updatePromotion(Long id, Promotion promotion) {
        Promotion existing = promotionRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Promotion not found with id: " + id);
        }
        existing.setTitle(promotion.getTitle());
        existing.setDescription(promotion.getDescription());
        existing.setCode(promotion.getCode());
        existing.setDiscountValue(promotion.getDiscountValue());
        existing.setExpiryDate(promotion.getExpiryDate());
        existing.setBanner(promotion.getBanner());
        existing.setBadge(promotion.getBadge());
        existing.setConditions(promotion.getConditions());
        existing.setCategoryId(promotion.getCategoryId());
        promotionRepository.persist(existing);
        return existing;
    }

    @Transactional
    public void deletePromotion(Long id) {
        Promotion existing = promotionRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Promotion not found with id: " + id);
        }
        promotionRepository.delete(existing);
    }

    @Transactional
    public Promotion validatePromotion(String code) {
        Promotion promo = promotionRepository.find("code", code).firstResult();
        if (promo == null) {
            throw new IllegalArgumentException("Mã khuyến mãi không tồn tại");
        }
        if (promo.getExpiryDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Mã khuyến mãi đã hết hạn");
        }
        return promo;
    }
}
