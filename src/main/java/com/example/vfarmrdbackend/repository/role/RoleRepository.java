package com.example.vfarmrdbackend.repository.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.role.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from roles r where r.role_name = :role_name", nativeQuery = true)
    Role getRoleByRole_name(@Param("role_name") String role_name);

    @Query(value = "select * from roles r where r.role_id = :role_id", nativeQuery = true)
    Role getRoleByRole_id(@Param("role_id") int role_id);

    @Query(value = "select r.role_id from roles r where r.role_name = :role_name", nativeQuery = true)
    int getRole_IdByRole_name(@Param("role_name") String role_name); 

    @Query(value = "select r.role_id from roles r order by r.role_id", nativeQuery = true)
    List<Integer> getAllRoles_id();
}