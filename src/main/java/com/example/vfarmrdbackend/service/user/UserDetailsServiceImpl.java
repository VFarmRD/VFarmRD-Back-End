package com.example.vfarmrdbackend.service.user;

import java.util.ArrayList;
import java.util.List;

import com.example.vfarmrdbackend.model.role.Role;
import com.example.vfarmrdbackend.model.user.User;
import com.example.vfarmrdbackend.model.user.UserRole;
import com.example.vfarmrdbackend.payload.user.UserDetailsImpl;
import com.example.vfarmrdbackend.repository.role.RoleRepository;
import com.example.vfarmrdbackend.repository.user.UserRepository;
import com.example.vfarmrdbackend.repository.user.UserRoleRepository;

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
  RoleRepository roleRepository;
  @Autowired
  UserRoleRepository userroleRepository;
  UserDetailsImpl userDetails = new UserDetailsImpl();

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String user_name) {
    User user = new User();
    List<Role> listRole = new ArrayList<Role>();
    try {
      user = userRepository.getUserByUser_name(user_name);
      List<UserRole> roleOfUser = userroleRepository.getAllRoleOfOneByUser_id(user.getUser_id());
      for (int i = 0; i < roleOfUser.size(); i++) {
        listRole.add(roleRepository.getRoleByRole_id(roleOfUser.get(i).getRole_id()));
      }
    } catch (UsernameNotFoundException e) {
      e.getMessage();
    }
    return userDetails.build(user,listRole, roleRepository.findAll());
  }

}
