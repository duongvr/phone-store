package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.AddressDTO;
import org.acme.entity.Address;
import org.acme.entity.User;
import org.acme.repository.AddressRepository;
import org.acme.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AddressService {

  @Inject
  AddressRepository addressRepository;

  @Inject
  UserRepository userRepository;

  /**
   * Get all addresses for a user
   */
  @Transactional
  public List<AddressDTO> getUserAddresses(Long userId) {
    return addressRepository.find("user.id", userId)
        .list()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Get address by ID
   */
  @Transactional
  public AddressDTO getAddressById(Long id) {
    Address address = addressRepository.findById(id);
    return address != null ? toDTO(address) : null;
  }

  /**
   * Get default address for user
   */
  @Transactional
  public AddressDTO getDefaultAddress(Long userId) {
    Address address = addressRepository.find("user.id = ?1 and isDefault = true", userId)
        .firstResult();
    return address != null ? toDTO(address) : null;
  }

  /**
   * Create new address
   */
  @Transactional
  public AddressDTO createAddress(Long userId, AddressDTO dto) {
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    // Validate input
    if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
      throw new IllegalArgumentException("Full name is required");
    }
    if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
      throw new IllegalArgumentException("Phone is required");
    }
    if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
      throw new IllegalArgumentException("Address is required");
    }

    Address address = new Address();
    address.user = user;
    address.name = dto.getName();
    address.fullName = dto.getFullName();
    address.phone = dto.getPhone();
    address.address = dto.getAddress();
    address.city = dto.getCity();
    address.district = dto.getDistrict();
    address.ward = dto.getWard();
    address.postalCode = dto.getPostalCode();
    address.isDefault = dto.getIsDefault() != null ? dto.getIsDefault() : false;

    // If this is the first address or marked as default, set it as default
    if (address.isDefault) {
      // Unset other default addresses
      addressRepository.find("user.id = ?1 and isDefault = true", userId)
          .list()
          .forEach(addr -> {
            addr.isDefault = false;
            addressRepository.persist(addr);
          });
    }

    addressRepository.persist(address);
    return toDTO(address);
  }

  /**
   * Update address
   */
  @Transactional
  public AddressDTO updateAddress(Long id, AddressDTO dto) {
    Address address = addressRepository.findById(id);
    if (address == null) {
      throw new IllegalArgumentException("Address not found");
    }

    address.name = dto.getName();
    address.fullName = dto.getFullName();
    address.phone = dto.getPhone();
    address.address = dto.getAddress();
    address.city = dto.getCity();
    address.district = dto.getDistrict();
    address.ward = dto.getWard();
    address.postalCode = dto.getPostalCode();

    // Handle default address change
    if (dto.getIsDefault() != null && dto.getIsDefault() && !address.isDefault) {
      // Unset other default addresses for this user
      addressRepository.find("user.id = ?1 and isDefault = true", address.user.id)
          .list()
          .forEach(addr -> {
            addr.isDefault = false;
            addressRepository.persist(addr);
          });
      address.isDefault = true;
    } else if (dto.getIsDefault() != null) {
      address.isDefault = dto.getIsDefault();
    }

    addressRepository.persist(address);
    return toDTO(address);
  }

  /**
   * Delete address
   */
  @Transactional
  public void deleteAddress(Long id) {
    Address address = addressRepository.findById(id);
    if (address == null) {
      throw new IllegalArgumentException("Address not found");
    }
    addressRepository.delete(address);
  }

  /**
   * Set address as default
   */
  @Transactional
  public void setDefaultAddress(Long userId, Long addressId) {
    Address address = addressRepository.findById(addressId);
    if (address == null) {
      throw new IllegalArgumentException("Address not found");
    }

    if (!address.user.id.equals(userId)) {
      throw new IllegalArgumentException("Address does not belong to this user");
    }

    // Unset other default addresses
    addressRepository.find("user.id = ?1 and isDefault = true", userId)
        .list()
        .forEach(addr -> {
          addr.isDefault = false;
          addressRepository.persist(addr);
        });

    address.isDefault = true;
    addressRepository.persist(address);
  }

  private AddressDTO toDTO(Address address) {
    return new AddressDTO(
        address.id,
        address.user != null ? address.user.id : null,
        address.name,
        address.fullName,
        address.phone,
        address.address,
        address.city,
        address.district,
        address.ward,
        address.postalCode,
        address.isDefault);
  }
}
