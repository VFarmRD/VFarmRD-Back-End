package com.example.vfarmrdbackend.repositories;

import java.util.List;

import com.example.vfarmrdbackend.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query(value = "select * from userrole u where u.user_id = :user_id", nativeQuery = true)
    UserRole getOtherUserRoleByUser_id(@Param("user_id") int user_id);

    @Query(value = "select * from userrole u where u.user_id = :user_id", nativeQuery = true)
    List<UserRole> getAllRoleOfOneByUser_id(@Param("user_id") int user_id);

}
