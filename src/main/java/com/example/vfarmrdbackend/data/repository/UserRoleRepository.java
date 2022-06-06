package com.example.vfarmrdbackend.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.business.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query(value = "select * from userrole u where u.user_id = :user_id ORDER BY u.role_id limit 1", nativeQuery = true)
    UserRole getUserRoleByUser_id(@Param("user_id") int user_id);

    @Query(value = "select * from userrole u where u.user_id = :user_id", nativeQuery = true)
    List<UserRole> getAllRoleOfOneByUser_id(@Param("user_id") int user_id);

    @Query(value = "select distinct r.role_name from userrole ur left join roles r  on ur.role_id = r.role_id where ur.user_id = :user_id ORDER BY r.role_name limit 1", nativeQuery = true)
    String getHighestRoleWithUser_Id(@Param("user_id") int user_id);
}
