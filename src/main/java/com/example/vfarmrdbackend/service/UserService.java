package com.example.vfarmrdbackend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Role;
import com.example.vfarmrdbackend.model.User;
import com.example.vfarmrdbackend.model.UserRole;
import com.example.vfarmrdbackend.payload.JwtResponse;
import com.example.vfarmrdbackend.payload.LoginRequest;
import com.example.vfarmrdbackend.payload.SignupRequest;
import com.example.vfarmrdbackend.payload.UserDetailsImpl;
import com.example.vfarmrdbackend.payload.UserRequest;
import com.example.vfarmrdbackend.repository.RoleRepository;
import com.example.vfarmrdbackend.repository.UserRepository;
import com.example.vfarmrdbackend.repository.UserRoleRepository;
import com.example.vfarmrdbackend.service.security.jwt.JwtUtils;

@Service
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    Date date;

    public User getUserInfo(int user_id) {
        User user = userRepository.getUserByUser_id(user_id);
        return user;
    }

    public boolean checkUserIsDisabled(String user_name) {
        if (userRepository.getUserByUser_name(user_name).isUser_status() == true) {
            return false;
        }
        return true;
    }

    public boolean checkUser_nameIsExisted(String user_name) {
        User user = userRepository.getUserByUser_name(user_name);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean checkEmailIsExisted(String user_name) {
        User user = userRepository.getUserByEmail(user_name);
        if (user != null) {
            return true;
        }
        return false;
    }

    public void setUser_role(int user_id, int role_id) {
        UserRole userrole = new UserRole();
        userrole.setUser_id(user_id);
        userrole.setRole_id(role_id);
        userRoleRepository.save(userrole);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUser_name(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getUser_id(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public void register(SignupRequest signUpRequest) {
        date = new Date();
        User user = new User();
        user.setUser_name(signUpRequest.getUser_name());
        user.setEmail(signUpRequest.getEmail());
        user.setFullname(signUpRequest.getFullname());
        user.setPhone(signUpRequest.getPhone());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setUser_status(true);
        user.setCreated_time(date);
        userRepository.save(user);
        int user_id = userRepository.getUserByUser_name(signUpRequest.getUser_name()).getUser_id();
        String role_name = signUpRequest.getRole_name();
        if (role_name.equals("admin")) {
            setUser_role(user_id, roleRepository.getRole_IdByRole_name("admin"));
        }
        if (role_name.equals("manager") || role_name.equals("admin")) {
            setUser_role(user_id, roleRepository.getRole_IdByRole_name("manager"));
        }
        if (role_name.equals("staff") || role_name.equals("admin")) {
            setUser_role(user_id, roleRepository.getRole_IdByRole_name("staff"));
        }
        user = userRepository.getUserByUser_name(signUpRequest.getUser_name());
        user.setRole_name(userRoleRepository.getHighestRoleWithUser_Id(user.getUser_id()));
        userRepository.save(user);
    }

    public void upgradeUserRoleToAdmin(int user_id, String role_name) {
        List<Role> listRole = roleRepository.getAllRoles();
        for (int i = 0; i < listRole.size(); i++) {
            if (listRole.get(i).getRole_id() != roleRepository.getRoleByRole_name(role_name)
                    .getRole_id()) {
                UserRole newuserrole = new UserRole();
                newuserrole.setUser_id(user_id);
                newuserrole.setRole_id(listRole.get(i).getRole_id());
                userRoleRepository.save(newuserrole);
            }
        }
    }

    public void degradeUserRoleFromAdmin(int user_id, String role_name) {
        List<UserRole> listUserRole = userRoleRepository.getAllRoleOfOneByUser_id(user_id);
        for (int i = 0; i < listUserRole.size(); i++) {
            if (listUserRole.get(i).getRole_id() != roleRepository.getRoleByRole_name(role_name)
                    .getRole_id()) {
                userRoleRepository.delete(listUserRole.get(i));
            }
        }
    }

    public boolean updateUser(UserRequest userRequest) {
        date = new Date();
        User user = userRepository.getUserByUser_id(userRequest.getUser_id());
        UserRole userrole = userRoleRepository.getUserRoleByUser_id(userRequest.getUser_id());
        int change_role_id = roleRepository.getRoleByRole_name(userRequest.getRole_name()).getRole_id();
        if (user != null && userrole != null) {
            if (user.getRole_name().equals("admin") && !userRequest.getRole_name().equals("admin")) {
                degradeUserRoleFromAdmin(userRequest.getUser_id(), userRequest.getRole_name());
            } else if (!user.getRole_name().equals("admin") && userRequest.getRole_name().equals("admin")) {
                upgradeUserRoleToAdmin(userRequest.getUser_id(), userRequest.getRole_name());
            } else {
                userrole.setRole_id(change_role_id);
                userRoleRepository.save(userrole);
            }
            user.setEmail(userRequest.getEmail());
            user.setFullname(userRequest.getFullname());
            user.setPhone(userRequest.getPhone());
            user.setRole_name(userRoleRepository.getHighestRoleWithUser_Id(user.getUser_id()));
            user.setPassword(encoder.encode(userRequest.getPassword()));
            user.setModified_time(date);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(int id) {
        User user = userRepository.getUserByUser_id(id);
        if (user != null) {
            date = new Date();
            user.setModified_time(date);
            user.setUser_status(false);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean recoverUser(int id) {
        User user = userRepository.getUserByUser_id(id);
        if (user != null) {
            date = new Date();
            user.setModified_time(date);
            user.setUser_status(true);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
