package com.example.vfarmrdbackend.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.business.model.Role;
import com.example.vfarmrdbackend.data.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleByRole_id(int role_id){
        return roleRepository.getRoleByRole_id(role_id);
    }

    public void createRole(String role_name){
        Role _role = new Role();
            _role.setRole_name(role_name);
            roleRepository.save(_role);
    }
}
