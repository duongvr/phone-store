package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.LoginRequest;
import org.acme.dto.LoginResponse;
import org.acme.dto.RegisterRequest;
import org.acme.dto.UserDTO;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.acme.util.JwtUtil;
import org.acme.util.PasswordUtil;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

  @Inject
  UserRepository userRepository;

  @Transactional
  public UserDTO register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email đã sử dụng");
    }

    if (request.getEmail() == null || request.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Không bỏ trống email");
    }
    if (request.getPassword() == null || request.getPassword().length() < 6) {
      throw new IllegalArgumentException("Mật khẩu phải ít nhất 6 ký tự");
    }
    if (request.getFullName() == null || request.getFullName().isEmpty()) {
      throw new IllegalArgumentException("Họ và tên không bỏ trống");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
    user.setFullName(request.getFullName());
    user.setPhone(request.getPhone());
    user.setRole("USER");
    user.setActive(true);

    userRepository.persist(user);
    return toDTO(user);
  }

  @Transactional
  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail());

    if (user == null || !PasswordUtil.verifyPassword(request.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Email hoặc mật khẩu không hợp lệ");
    }

    if (!user.getActive()) {
      throw new IllegalArgumentException("Tài khoản đã bị tắt");
    }

    String token = JwtUtil.generateToken(user.id, user.getEmail(), user.getRole());

    return new LoginResponse(
        user.id,
        user.getEmail(),
        user.getFullName(),
        token,
        user.getRole());
  }

  @Transactional
  public UserDTO getUserById(Long id) {
    User user = userRepository.findById(id);
    return user != null ? toDTO(user) : null;
  }

  @Transactional
  public UserDTO getUserByEmail(String email) {
    User user = userRepository.findByEmail(email);
    return user != null ? toDTO(user) : null;
  }

  @Transactional
  public List<UserDTO> getAllUsers() {
    return userRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  public UserDTO updateProfile(Long id, UserDTO dto) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("Không tìm thấy người dùng");
    }

    user.setFullName(dto.getFullName());
    user.setPhone(dto.getPhone());
    user.setAvatar(dto.getAvatar());

    userRepository.persist(user);
    return toDTO(user);
  }

  @Transactional
  public void changePassword(Long id, String oldPassword, String newPassword) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("Không tìm thấy người dùng");
    }

    if (!PasswordUtil.verifyPassword(oldPassword, user.getPassword())) {
      throw new IllegalArgumentException("Mật khẩu cũ không đúng");
    }

    if (newPassword == null || newPassword.length() < 6) {
      throw new IllegalArgumentException("Mật khẩu mới phải dài hơn 6 ký tự");
    }

    user.setPassword(PasswordUtil.hashPassword(newPassword));
    userRepository.persist(user);
  }

  @Transactional
  public void deactivateUser(Long id) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("Không tìm thấy người dùng");
    }
    user.setActive(false);
    userRepository.persist(user);
  }

  @Transactional
  public void activateUser(Long id) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("Không tìm thấy người dùng");
    }
    user.setActive(true);
    userRepository.persist(user);
  }

  private UserDTO toDTO(User user) {
    return new UserDTO(
        user.id,
        user.getEmail(),
        user.getFullName(),
        user.getPhone(),
        user.getAvatar(),
        user.getRole(),
        user.getActive());
  }
}
