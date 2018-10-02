package com.omarnyte.authentication;

import static org.junit.Assert.assertFalse; 
import static org.junit.Assert.assertTrue; 
import org.junit.Test; 

import com.omarnyte.authentication.Credentials;

public class CredentialsTest {
  private final static String USERNAME = "clever_username";
  private final static String PASSWORD = "secure_password";

  private static Credentials credentials = new Credentials(USERNAME, PASSWORD);

  @Test
  public void returnsTrueWhenCredentialsMatch() {
    assertTrue(credentials.areValidCredentials(USERNAME, PASSWORD));
  }

  @Test
  public void returnsFalseWhenCredentialsDoNotMatch() {
    String username = "nonexistent_useraname";
    String password = "incorrect_password";
    assertFalse(credentials.areValidCredentials(username, password));
  }

}