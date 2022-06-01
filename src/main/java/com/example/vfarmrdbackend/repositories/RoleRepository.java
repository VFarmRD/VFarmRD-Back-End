package com.example.vfarmrdbackend.repositories;

import com.example.vfarmrdbackend.models.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from roles r where r.role_name = :role_name", nativeQuery = true)
    Role getRoleByRole_name(@Param("role_name") String role_name);

    @Query(value = "select * from roles r where r.role_id = :role_id", nativeQuery = true)
    Role getRoleByRole_id(@Param("role_id") int role_id);

    @Query(value = "select * from roles r", nativeQuery = true)
    List<Role> getAllRoles();
}