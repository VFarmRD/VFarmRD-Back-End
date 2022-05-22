package com.example.vfarmrdbackend.security.services;

import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.repositories.RoleRepository;
import com.example.vfarmrdbackend.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  private RoleRepository rolerepo;
  UserDetailsImpl userDetails = new UserDetailsImpl();

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String user_name) {
    User user = new User();
    try {
      user = userRepository.findUserByUser_name(user_name);
    } catch (UsernameNotFoundException e) {
      e.getMessage();
    }
    return userDetails.build(user, rolerepo.findAll());
  }

}
