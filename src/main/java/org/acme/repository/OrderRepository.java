package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

}
