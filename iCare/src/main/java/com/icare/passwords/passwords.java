package main.java.com.icare.passwords;

import java.security.MessageDigest;

public class passwords {

  public static String passwordHash(String password) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-256");
      md.update(password.getBytes("UTF-8"));
      byte[] digest = md.digest();
      return String.format("%064x", new java.math.BigInteger(1, digest));
    } catch (Exception e) {
      return null;
    }
  }
  
  public static boolean comparePassword(String hashed, String unhashed) {
    return hashed.equals(passwordHash(unhashed));
  }
  
}
