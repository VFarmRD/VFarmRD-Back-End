package com.example.vfarmrdbackend.service.user;

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

import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.user.User;
import com.example.vfarmrdbackend.model.user.UserRole;
import com.example.vfarmrdbackend.payload.user.UserDetailsImpl;
import com.example.vfarmrdbackend.payload.user.UserRequest;
import com.example.vfarmrdbackend.payload.user.LoginRequest;
import com.example.vfarmrdbackend.payload.user.SignupRequest;
import com.example.vfarmrdbackend.payload.others.JwtResponse;
import com.example.vfarmrdbackend.repository.role.RoleRepository;
import com.example.vfarmrdbackend.repository.user.UserRepository;
import com.example.vfarmrdbackend.repository.user.UserRoleRepository;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.others.JwtService;
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
    LogService logService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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
                userRepository.getUserByUser_id(userDetails.getUser_id()).getFullname(),
                userDetails.getEmail(),
                roles);
    }

    public void register(SignupRequest signUpRequest) {
        User user = new User();
        user.setUser_name(signUpRequest.getUser_name());
        user.setEmail(signUpRequest.getEmail());
        user.setFullname(signUpRequest.getFullname());
        user.setPhone(signUpRequest.getPhone());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setUser_status(true);
        user.setCreated_time(new Date());
        userRepository.save(user);
        int user_id = userRepository.getUserByUser_name(signUpRequest.getUser_name()).getUser_id();
        String role_name = signUpRequest.getRole_name();
        if (role_name.equals("admin")) {
            setUser_role(user_id, roleRepository.getRole_IdByRole_name("admin"));
        }
        if (role_name.equals("manager") || role_name.equals("admin")) {
            setUser_role(user_id, roleRepository.getRole_IdByRole_name("manager"));
        }
        if (role_name.equals("staff") || role_name.equals("manager") || role_name.equals("admin")) {
            setUser_role(user_id, roleRepository.getRole_IdByRole_name("staff"));
        }
        user = userRepository.getUserByUser_name(signUpRequest.getUser_name());
        user.setRole_name(userRoleRepository.getHighestRoleWithUser_Id(user.getUser_id()));
        userRepository.save(user);
    }

    public void addUserRole(int user_id, int role_id) {
        UserRole newuserrole = new UserRole();
        newuserrole.setUser_id(user_id);
        newuserrole.setRole_id(role_id);
        userRoleRepository.save(newuserrole);
    }

    public void deleteUserRole(int user_id, int role_id) {
        UserRole olduserrole = userRoleRepository.getUserRoleByUser_idAndRole_id(user_id, role_id);
        userRoleRepository.delete(olduserrole);
    }

    public void changeUserRole(int user_id, int change_role_id) {
        int highest_user_role = userRoleRepository.getHighestRole_idWithUser_Id(user_id);
        if (change_role_id == 1 && highest_user_role == 3) {
            addUserRole(user_id, 1);
            addUserRole(user_id, 2);
        }
        if (change_role_id == 1 && highest_user_role == 2) {
            addUserRole(user_id, 1);
        }
        if (change_role_id == 2 && highest_user_role == 1) {
            deleteUserRole(user_id, 1);
        }
        if (change_role_id == 2 && highest_user_role == 3) {
            addUserRole(user_id, change_role_id);
        }
        if (change_role_id == 3 && highest_user_role == 1) {
            deleteUserRole(user_id, 1);
            deleteUserRole(user_id, 2);
        }
        if (change_role_id == 3 && highest_user_role == 2) {
            deleteUserRole(user_id, 2);
        }
    }

    public boolean updateUser(UserRequest userRequest, String jwt) {
        User user = userRepository.getUserByUser_id(userRequest.getUser_id());
        UserRole userrole = userRoleRepository.getUserRoleByUser_id(userRequest.getUser_id());
        int change_role_id = roleRepository.getRoleByRole_name(userRequest.getRole_name()).getRole_id();
        if (user != null && userrole != null) {
            changeUserRole(userRequest.getUser_id(), change_role_id);
            user.setEmail(userRequest.getEmail());
            user.setFullname(userRequest.getFullname());
            user.setPhone(userRequest.getPhone());
            user.setRole_name(userRoleRepository.getHighestRoleWithUser_Id(user.getUser_id()));
            user.setPassword(encoder.encode(userRequest.getPassword()));
            user.setModified_time(new Date());
            userRepository.save(user);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "USER",
                    "UPDATE",
                    String.valueOf(userRequest.getUser_id()),
                    new Date()));
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(int id, String jwt) {
        User user = userRepository.getUserByUser_id(id);
        if (user != null) {
            user.setModified_time(new Date());
            user.setUser_status(false);
            userRepository.save(user);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "USER",
                    "DEACTIVATE",
                    String.valueOf(id),
                    new Date()));
            return true;
        } else {
            return false;
        }
    }

    public boolean recoverUser(int id, String jwt) {
        User user = userRepository.getUserByUser_id(id);
        if (user != null) {
            user.setModified_time(new Date());
            user.setUser_status(true);
            userRepository.save(user);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "USER",
                    "RECOVERY",
                    String.valueOf(id),
                    new Date()));
            return true;
        } else {
            return false;
        }
    }

}
