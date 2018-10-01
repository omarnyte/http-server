package com.omarnyte.authentication;

public class Credentials {
  private String password;
  private String username;
  
  public Credentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public boolean areValidCredentials(String username, String password) {
    return username.equals(this.username) && password.equals(this.password);
  }
  
}