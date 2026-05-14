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

  @Transactional
  public List<AddressDTO> getUserAddresses(Long userId) {
    return addressRepository.find("user.id", userId)
            .list()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }

  @Transactional
  public AddressDTO getAddressById(Long id) {
    Address address = addressRepository.findById(id);
    return address != null ? toDTO(address) : null;
  }

  @Transactional
  public AddressDTO getDefaultAddress(Long userId) {
    Address address = addressRepository.find("user.id = ?1 and isDefault = true", userId)
            .firstResult();
    return address != null ? toDTO(address) : null;
  }

  @Transactional
  public void setDefaultAddress(Long userId, Long addressId) {
    List<Address> addresses = addressRepository.find("user.id", userId).list();
    addresses.forEach(a -> a.setIsDefault(false));

    Address target = addressRepository.findById(addressId);
    if (target == null || !target.getUser().id.equals(userId)) {
      throw new IllegalArgumentException("Địa chỉ không tồn tại hoặc không thuộc về người dùng");
    }

    target.setIsDefault(true);
    addressRepository.persist(target);
  }

  @Transactional
  public AddressDTO createAddress(Long userId, AddressDTO dto) {
    User user = userRepository.findById(userId);
    if (user == null) throw new IllegalArgumentException("Không tìm thấy người dùng");

    if (dto.getFullName() == null || dto.getFullName().isEmpty())
      throw new IllegalArgumentException("Họ tên không bỏ trống");
    if (dto.getPhone() == null || dto.getPhone().isEmpty())
      throw new IllegalArgumentException("Số điện thoại không bỏ trống");
    if (dto.getAddress() == null || dto.getAddress().isEmpty())
      throw new IllegalArgumentException("Địa chỉ không bỏ trống");

    Address address = new Address();
    address.setUser(user);
    address.setName(dto.getName());
    address.setFullName(dto.getFullName());
    address.setPhone(dto.getPhone());
    address.setAddress(dto.getAddress());
    address.setCity(dto.getCity());
    address.setDistrict(dto.getDistrict());
    address.setWard(dto.getWard());
    address.setPostalCode(dto.getPostalCode());
    address.setIsDefault(dto.getIsDefault() != null ? dto.getIsDefault() : false);

    if (address.getIsDefault()) {
      addressRepository.find("user.id = ?1 and isDefault = true", userId)
              .list()
              .forEach(a -> {
                a.setIsDefault(false);
                addressRepository.persist(a);
              });
    }

    addressRepository.persist(address);
    return toDTO(address);
  }

  @Transactional
  public AddressDTO updateAddress(Long id, AddressDTO dto) {
    Address address = addressRepository.findById(id);
    if (address == null) throw new IllegalArgumentException("Không tìm thấy địa chỉ");

    address.setName(dto.getName());
    address.setFullName(dto.getFullName());
    address.setPhone(dto.getPhone());
    address.setAddress(dto.getAddress());
    address.setCity(dto.getCity());
    address.setDistrict(dto.getDistrict());
    address.setWard(dto.getWard());
    address.setPostalCode(dto.getPostalCode());

    if (dto.getIsDefault() != null && dto.getIsDefault() && !address.getIsDefault()) {
      addressRepository.find("user.id = ?1 and isDefault = true", address.getUser().id)
              .list()
              .forEach(a -> {
                a.setIsDefault(false);
                addressRepository.persist(a);
              });
      address.setIsDefault(true);
    } else if (dto.getIsDefault() != null) {
      address.setIsDefault(dto.getIsDefault());
    }

    addressRepository.persist(address);
    return toDTO(address);
  }

  @Transactional
  public void deleteAddress(Long id) {
    Address address = addressRepository.findById(id);
    if (address == null) throw new IllegalArgumentException("Không tìm thấy địa chỉ");
    addressRepository.delete(address);
  }

  private AddressDTO toDTO(Address address) {
    AddressDTO dto = new AddressDTO();
    dto.setId(address.id);
    dto.setUserId(address.getUser().id);
    dto.setName(address.getName());
    dto.setFullName(address.getFullName());
    dto.setPhone(address.getPhone());
    dto.setAddress(address.getAddress());
    dto.setCity(address.getCity());
    dto.setDistrict(address.getDistrict());
    dto.setWard(address.getWard());
    dto.setPostalCode(address.getPostalCode());
    dto.setIsDefault(address.getIsDefault());
    return dto;
  }
}
