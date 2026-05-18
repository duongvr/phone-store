package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.LoginRequest;
import org.acme.dto.LoginResponse;
import org.acme.dto.RegisterRequest;
import org.acme.dto.UserDTO;
import org.acme.service.UserService;
import org.acme.util.JwtUtil;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  @Inject
  UserService userService;

  /**
   * Register new user
   */
  @POST
  @Path("/register")
  public Response register(RegisterRequest request) {
    try {
      if (request.getEmail() == null || request.getEmail().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống email"))
            .build();
      }
      if (request.getPassword() == null || request.getPassword().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống mật khẩu"))
            .build();
      }
      if (request.getFullName() == null || request.getFullName().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống họ và tên"))
            .build();
      }

      UserDTO user = userService.register(request);
      return Response.status(Response.Status.CREATED).entity(user).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi đăng ký người dùng mới: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Login user
   */
  @POST
  @Path("/login")
  public Response login(LoginRequest request) {
    try {
      if (request.getEmail() == null || request.getEmail().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống email"))
            .build();
      }
      if (request.getPassword() == null || request.getPassword().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống mật khẩu"))
            .build();
      }

      LoginResponse response = userService.login(request);
      return Response.ok(response).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.UNAUTHORIZED)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi đăng nhập: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Get user by ID
   */
  @GET
  @Path("/{id}")
  public Response getUserById(@PathParam("id") Long id) {
    try {
      UserDTO user = userService.getUserById(id);
      if (user == null) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse("Không tìm thấy người dùng"))
            .build();
      }
      return Response.ok(user).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi lấy dữ liệu người dùng: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Get user by email
   */
  @GET
  @Path("/email/{email}")
  public Response getUserByEmail(@PathParam("email") String email) {
    try {
      UserDTO user = userService.getUserByEmail(email);
      if (user == null) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse("Không tìm thấy người dùng"))
            .build();
      }
      return Response.ok(user).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi lấy thông tin người dùng: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Get all users (admin only)
   */
  @GET
  public Response getAllUsers() {
    try {
      List<UserDTO> users = userService.getAllUsers();
      return Response.ok(users).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi lấy thông tin người dùng: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Update user profile
   */
  @PUT
  @Path("/{id}")
  public Response updateProfile(@PathParam("id") Long id, UserDTO dto) {
    try {
      UserDTO updated = userService.updateProfile(id, dto);
      return Response.ok(updated).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi cập nhật thông tin người dùng: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Change password
   */
  @POST
  @Path("/{id}/change-password")
  public Response changePassword(@PathParam("id") Long id, ChangePasswordRequest request) {
    try {
      if (request.getOldPassword() == null || request.getOldPassword().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống mật khẩu cũ"))
            .build();
      }
      if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Không bỏ trống mật khẩu mới"))
            .build();
      }

      userService.changePassword(id, request.getOldPassword(), request.getNewPassword());
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi đổi mật khẩu: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Deactivate user account
   */
  @POST
  @Path("/{id}/deactivate")
  public Response deactivateUser(@PathParam("id") Long id) {
    try {
      userService.deactivateUser(id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi tắt: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Activate user account (admin only)
   */
  @POST
  @Path("/{id}/activate")
  public Response activateUser(@PathParam("id") Long id) {
    try {
      userService.activateUser(id);
      return Response.noContent().build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi kích hoạt: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Forgot password
   */
  @POST
  @Path("/forgot-password")
  public Response forgotPassword(ForgotPasswordRequest request) {
    try {
      if (request.getEmail() == null || request.getEmail().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Yêu cầu nhập email"))
            .build();
      }
      userService.forgotPassword(request.getEmail());
      return Response.ok(new SuccessResponse("Link khôi phục mật khẩu đã được gửi tới email của bạn")).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi khi xử lý yêu cầu: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Verify token
   */
  @POST
  @Path("/verify-token")
  public Response verifyToken(TokenRequest request) {
    try {
      if (request.getToken() == null || request.getToken().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Yêu cầu token"))
            .build();
      }

      Long userId = JwtUtil.validateToken(request.getToken());
      if (userId == null) {
        return Response.status(Response.Status.UNAUTHORIZED)
            .entity(new ErrorResponse("Token hết hạn hoặc không đúng "))
            .build();
      }

      return Response.ok(new TokenVerificationResponse(userId, true)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse("Lỗi xác thực token: " + e.getMessage()))
          .build();
    }
  }

  /**
   * Change password request class
   */
  public static class ChangePasswordRequest {
    public String oldPassword;
    public String newPassword;

    public String getOldPassword() {
      return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
      this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
      return newPassword;
    }

    public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
    }
  }

  /**
   * Token request class
   */
  public static class TokenRequest {
    public String token;

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }
  }

  /**
   * Token verification response class
   */
  public static class TokenVerificationResponse {
    public Long userId;
    public Boolean valid;

    public TokenVerificationResponse(Long userId, Boolean valid) {
      this.userId = userId;
      this.valid = valid;
    }
  }

  /**
   * Forgot password request class
   */
  public static class ForgotPasswordRequest {
    public String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
  }

  /**
   * Success response class
   */
  public static class SuccessResponse {
    public String message;
    public SuccessResponse(String message) { this.message = message; }
  }

  /**
   * Error response class
   */
  public static class ErrorResponse {
    public String message;

    public ErrorResponse(String message) {
      this.message = message;
    }
  }
}
