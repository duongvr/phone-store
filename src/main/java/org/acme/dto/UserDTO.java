package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  public Long id;
  public String email;
  public String fullName;
  public String phone;
  public String avatar;
  public String role;
  public Boolean active;
}
