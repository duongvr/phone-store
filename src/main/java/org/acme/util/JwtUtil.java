package org.acme.util;

import java.util.Base64;

/**
 * Simple JWT utility for token generation and validation
 * Note: For production, use a proper JWT library like io.jsonwebtoken:jjwt
 */
public class JwtUtil {

  private static final String SECRET = "phone-store-secret-key-2024";
  private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

  /**
   * Generate a simple JWT token
   */
  public static String generateToken(Long userId, String email, String role) {
    long issuedAt = System.currentTimeMillis();
    long expiresAt = issuedAt + EXPIRATION_TIME;

    String payload = userId + ":" + email + ":" + role + ":" + expiresAt;
    String encoded = Base64.getEncoder().encodeToString(payload.getBytes());
    String signature = generateSignature(encoded);

    return encoded + "." + signature;
  }

  /**
   * Validate and extract user ID from token
   */
  public static Long validateToken(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length != 2) {
        return null;
      }

      String encoded = parts[0];
      String signature = parts[1];

      // Verify signature
      if (!signature.equals(generateSignature(encoded))) {
        return null;
      }

      // Decode payload
      String payload = new String(Base64.getDecoder().decode(encoded));
      String[] data = payload.split(":");

      if (data.length < 4) {
        return null;
      }

      // Check expiration
      long expiresAt = Long.parseLong(data[3]);
      if (System.currentTimeMillis() > expiresAt) {
        return null;
      }

      return Long.parseLong(data[0]);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Extract email from token
   */
  public static String extractEmail(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length != 2) {
        return null;
      }

      String encoded = parts[0];
      String payload = new String(Base64.getDecoder().decode(encoded));
      String[] data = payload.split(":");

      if (data.length >= 2) {
        return data[1];
      }
    } catch (Exception e) {
      // Ignore
    }
    return null;
  }

  private static String generateSignature(String encoded) {
    String data = encoded + SECRET;
    return Base64.getEncoder().encodeToString(data.getBytes());
  }
}
