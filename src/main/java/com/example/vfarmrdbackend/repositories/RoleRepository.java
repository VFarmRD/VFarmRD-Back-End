package com.example.vfarmrdbackend.repositories;

import java.util.List;

import com.example.vfarmrdbackend.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "SELECT r.* FROM roles r WHERE r.role_name LIKE %:keyword% ", nativeQuery = true)
    List<Role> findRoleNameByKeyword(@Param("keyword") String keyword);
}