package com.example.vfarmrdbackend.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.role.Role;
import com.example.vfarmrdbackend.repository.role.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Role getRoleByRole_id(int role_id) {
        try {
            return roleRepository.getRoleByRole_id(role_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createRole(String role_name) {
        try {
            Role role = new Role();
            role.setRole_name(role_name);
            roleRepository.save(role);
        } catch (Exception e) {
            throw e;
        }
    }
}
