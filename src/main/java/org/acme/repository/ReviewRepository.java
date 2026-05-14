package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Review;

@ApplicationScoped
public class ReviewRepository implements PanacheRepository<Review> {

}
