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
    public List<Promotion> getPromotions(){
        return promotionRepository.findAll().stream().toList();
    }

    @Transactional
    public List<Promotion> getPromotionInProduct(Long productId){
        return promotionRepository.find("product.id", productId).list();
    }

    @Transactional
    public Promotion createPromotion(Promotion promotion){

        return promotion;
    }
}
