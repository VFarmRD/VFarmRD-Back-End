package com.example.vfarmrdbackend.business.service;

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

import com.example.vfarmrdbackend.business.model.Role;
import com.example.vfarmrdbackend.business.model.User;
import com.example.vfarmrdbackend.business.model.UserRole;
import com.example.vfarmrdbackend.business.payload.JwtResponse;
import com.example.vfarmrdbackend.business.payload.LoginRequest;
import com.example.vfarmrdbackend.business.payload.SignupRequest;
import com.example.vfarmrdbackend.business.payload.UserDetailsImpl;
import com.example.vfarmrdbackend.business.payload.UserRequest;
import com.example.vfarmrdbackend.business.service.security.jwt.JwtUtils;
import com.example.vfarmrdbackend.data.repository.RoleRepository;
import com.example.vfarmrdbackend.data.repository.UserRepository;
import com.example.vfarmrdbackend.data.repository.UserRoleRepository;

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

    void setUser_role(int user_id, int role_id) {
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
        User user = new User(
                0,
                signUpRequest.getUser_name(),
                signUpRequest.getEmail(),
                signUpRequest.getFullname(),
                signUpRequest.getPhone(),
                encoder.encode(signUpRequest.getPassword()),
                true,
                date,
                null,
                "");
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
        User _user = userRepository.getUserByUser_id(userRequest.getUser_id());
        UserRole _userrole = userRoleRepository.getUserRoleByUser_id(userRequest.getUser_id());
        int change_role_id = roleRepository.getRoleByRole_name(userRequest.getRole_name()).getRole_id();
        if (_user != null && _userrole != null) {
            if (_user.getRole_name().equals("admin") && !userRequest.getRole_name().equals("admin")) {
                degradeUserRoleFromAdmin(userRequest.getUser_id(), userRequest.getRole_name());
            } else if (!_user.getRole_name().equals("admin") && userRequest.getRole_name().equals("admin")) {
                upgradeUserRoleToAdmin(userRequest.getUser_id(), userRequest.getRole_name());
            } else {
                _userrole.setRole_id(change_role_id);
                userRoleRepository.save(_userrole);
            }
            _user.setEmail(userRequest.getEmail());
            _user.setFullname(userRequest.getFullname());
            _user.setPhone(userRequest.getPhone());
            _user.setRole_name(userRoleRepository.getHighestRoleWithUser_Id(_user.getUser_id()));
            _user.setPassword(encoder.encode(userRequest.getPassword()));
            _user.setModified_time(date);
            userRepository.save(_user);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(int id) {
        User _user = userRepository.getUserByUser_id(id);
        if (_user != null) {
            date = new Date();
            _user.setModified_time(date);
            _user.setUser_status(false);
            userRepository.save(_user);
            return true;
        } else {
            return false;
        }
    }

    public boolean recoverUser(int id) {
        User _user = userRepository.getUserByUser_id(id);
        if (_user != null) {
            date = new Date();
            _user.setModified_time(date);
            _user.setUser_status(true);
            userRepository.save(_user);
            return true;
        } else {
            return false;
        }
    }
}
