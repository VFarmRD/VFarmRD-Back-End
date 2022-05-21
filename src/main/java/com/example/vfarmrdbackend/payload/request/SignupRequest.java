package com.example.vfarmrdbackend.payload.request;

import java.util.List;

import javax.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  private String user_name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  private List<String> role;

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRole() {
    return this.role;
  }

  public void listRole(List<String> role) {
    this.role = role;
  }
}
