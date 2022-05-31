package com.example.vfarmrdbackend.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.vfarmrdbackend.models.Role;
import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.payload.JwtResponse;
import com.example.vfarmrdbackend.payload.LoginRequest;
import com.example.vfarmrdbackend.payload.SignupRequest;
import com.example.vfarmrdbackend.repositories.RoleRepository;
import com.example.vfarmrdbackend.repositories.UserRepository;
import com.example.vfarmrdbackend.security.jwt.JwtUtils;
import com.example.vfarmrdbackend.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  Date date = new Date();

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUser_name(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userRepository.getUserByUser_name(userDetails.getUsername());
    if (user.isUser_status() == false) {
      return new ResponseEntity<>(
          "Your account is disabled!",
          HttpStatus.GONE);
    }
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getUser_id(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @PostMapping("/create")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    User user = userRepository.getUserByUser_name(signUpRequest.getUser_name());
    if (user != null) {
      return new ResponseEntity<>("This username is already registered!", HttpStatus.CONFLICT);
    }
    user = userRepository.getUserByEmail(signUpRequest.getEmail());
    if (user != null) {
      return new ResponseEntity<>("This email is already registered!", HttpStatus.IM_USED);
    }
    user = new User();
    user.setUser_name(signUpRequest.getUser_name());
    user.setEmail(signUpRequest.getEmail());
    user.setFullname(signUpRequest.getFullname());
    user.setPhone(signUpRequest.getPhone());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));

    String role_name = signUpRequest.getRole_name();
    List<Role> roles = new ArrayList<>();
    Role adminRole = roleRepository.getRoleByRole_name("admin");
    Role staffRole = roleRepository.getRoleByRole_name("staff");
    Role managerRole = roleRepository.getRoleByRole_name("manager");
    if (role_name.equals("admin")) {
      roles.add(adminRole);
      roles.add(staffRole);
      roles.add(managerRole);
    } else if (role_name.equals("staff")) {
      roles.add(staffRole);
    } else if (role_name.equals("manager")) {
      roles.add(managerRole);
    }
    user.setUser_status(true);
    user.setRoles(roles);
    user.setCreated_time(date);
    userRepository.save(user);
    return new ResponseEntity<>("Sign up account completed!", HttpStatus.OK);
  }
}
