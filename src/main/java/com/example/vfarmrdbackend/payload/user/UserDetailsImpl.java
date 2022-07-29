package com.example.vfarmrdbackend.payload.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.vfarmrdbackend.model.role.Role;
import com.example.vfarmrdbackend.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  User user;

  private int user_id;

  private String user_name;

  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(int user_id, String user_name, String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.user_id = user_id;
    this.user_name = user_name;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public UserDetailsImpl() {

  }

  public UserDetailsImpl build(User user,List<Role> userRole, List<Role> allRoles) {

    List<GrantedAuthority> authorities = new ArrayList<>();
    
    for (Role role : allRoles) {
      for (Role userrole : userRole) {
        if (userrole.equals(role)) {
          authorities.add(new SimpleGrantedAuthority(role.getRole_name()));
        }
      }
    }

    return new UserDetailsImpl(
        user.getUser_id(),
        user.getUser_name(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public int getUser_id() {
    return user_id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return user_name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(user_id, user.user_id);
  }
}
