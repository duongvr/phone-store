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

  /**
   * Register new user
   */
  @Transactional
  public UserDTO register(RegisterRequest request) {
    // Check if email already exists
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already registered");
    }

    // Validate input
    if (request.getEmail() == null || request.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email is required");
    }
    if (request.getPassword() == null || request.getPassword().length() < 6) {
      throw new IllegalArgumentException("Password must be at least 6 characters");
    }
    if (request.getFullName() == null || request.getFullName().isEmpty()) {
      throw new IllegalArgumentException("Full name is required");
    }

    User user = new User();
    user.email = request.getEmail();
    user.password = PasswordUtil.hashPassword(request.getPassword());
    user.fullName = request.getFullName();
    user.phone = request.getPhone();
    user.role = "USER";
    user.active = true;

    userRepository.persist(user);
    return toDTO(user);
  }

  /**
   * Login user
   */
  @Transactional
  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail());

    if (user == null || !PasswordUtil.verifyPassword(request.getPassword(), user.password)) {
      throw new IllegalArgumentException("Invalid email or password");
    }

    if (!user.active) {
      throw new IllegalArgumentException("User account is inactive");
    }

    String token = JwtUtil.generateToken(user.id, user.email, user.role);

    return new LoginResponse(
        user.id,
        user.email,
        user.fullName,
        token,
        user.role);
  }

  /**
   * Get user by ID
   */
  @Transactional
  public UserDTO getUserById(Long id) {
    User user = userRepository.findById(id);
    return user != null ? toDTO(user) : null;
  }

  /**
   * Get user by email
   */
  @Transactional
  public UserDTO getUserByEmail(String email) {
    User user = userRepository.findByEmail(email);
    return user != null ? toDTO(user) : null;
  }

  /**
   * Get all users (admin only)
   */
  @Transactional
  public List<UserDTO> getAllUsers() {
    return userRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Update user profile
   */
  @Transactional
  public UserDTO updateProfile(Long id, UserDTO dto) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    user.fullName = dto.getFullName();
    user.phone = dto.getPhone();
    user.avatar = dto.getAvatar();

    userRepository.persist(user);
    return toDTO(user);
  }

  /**
   * Change password
   */
  @Transactional
  public void changePassword(Long id, String oldPassword, String newPassword) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    if (!PasswordUtil.verifyPassword(oldPassword, user.password)) {
      throw new IllegalArgumentException("Old password is incorrect");
    }

    if (newPassword == null || newPassword.length() < 6) {
      throw new IllegalArgumentException("New password must be at least 6 characters");
    }

    user.password = PasswordUtil.hashPassword(newPassword);
    userRepository.persist(user);
  }

  /**
   * Deactivate user account
   */
  @Transactional
  public void deactivateUser(Long id) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    user.active = false;
    userRepository.persist(user);
  }

  /**
   * Activate user account (admin only)
   */
  @Transactional
  public void activateUser(Long id) {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    user.active = true;
    userRepository.persist(user);
  }

  private UserDTO toDTO(User user) {
    return new UserDTO(
        user.id,
        user.email,
        user.fullName,
        user.phone,
        user.avatar,
        user.role,
        user.active);
  }
}
