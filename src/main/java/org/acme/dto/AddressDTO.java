package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
  public Long id;
  public Long userId;
  public String name;
  public String fullName;
  public String phone;
  public String address;
  public String city;
  public String district;
  public String ward;
  public String postalCode;
  public Boolean isDefault;
}
