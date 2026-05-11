package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Address;

import java.util.List;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {

  public List<Address> findByUserId(Long userId) {
    return find("user.id", userId).list();
  }

  public Address findDefaultByUserId(Long userId) {
    return find("user.id = ?1 and isDefault = true", userId).firstResult();
  }
}
