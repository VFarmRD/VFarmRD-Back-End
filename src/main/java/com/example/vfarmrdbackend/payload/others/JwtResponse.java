package com.example.vfarmrdbackend.payload.others;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private int user_id;
  private String user_name;
  private String fullname;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, int user_id, String user_name, String fullname, String email,
      List<String> roles) {
    this.token = accessToken;
    this.user_id = user_id;
    this.user_name = user_name;
    this.fullname = fullname;
    this.email = email;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
