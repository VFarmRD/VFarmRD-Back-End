package com.example.vfarmrdbackend.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.vfarmrdbackend.models.Role;
import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.payload.request.LoginRequest;
import com.example.vfarmrdbackend.payload.request.SignupRequest;
import com.example.vfarmrdbackend.payload.response.JwtResponse;
import com.example.vfarmrdbackend.repositories.RoleRepository;
import com.example.vfarmrdbackend.repositories.UserRepository;
import com.example.vfarmrdbackend.security.jwt.JwtUtils;
import com.example.vfarmrdbackend.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
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

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUser_name(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getUser_id(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    User user = userRepository.findUserByUser_name(signUpRequest.getUser_name());
    if (user != null) {
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
    user = userRepository.findUserByEmail(signUpRequest.getEmail());
    if (user != null) {
      return new ResponseEntity<>(null, HttpStatus.IM_USED);
    }
    user = new User(signUpRequest.getUser_name(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    List<String> strRoles = signUpRequest.getRole();
    List<Role> roles = new ArrayList<>();
    strRoles.forEach(role -> {
      switch (role) {
        case "admin":
          Role adminRole = roleRepository.findRoleByRole_name("admin");
          roles.add(adminRole);
          break;
        case "staff":
          Role staffRole = roleRepository.findRoleByRole_name("staff");
          roles.add(staffRole);
          break;
        case "manager":
          Role managerRole = roleRepository.findRoleByRole_name("manager");
          roles.add(managerRole);
          break;
        default:
          Role userRole = roleRepository.findRoleByRole_name("user");
          roles.add(userRole);
      }
    });
    user.listRoles(roles);
    user.setCreated_time(date);
    userRepository.save(user);
    return new ResponseEntity<>("Sign up account completed!", HttpStatus.OK);
  }
}
