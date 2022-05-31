package com.example.vfarmrdbackend.repositories;

import java.util.List;

import com.example.vfarmrdbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users u where lower(u.user_name) like lower(:user_name)", nativeQuery = true)
    User getUserByUser_name(@Param("user_name") String user_name);

    @Query(value = "select * from users u where u.user_id = :user_id", nativeQuery = true)
    User getUserByUser_id(@Param("user_id") int user_id);

    @Query(value = "select * from users u where lower(u.email) like lower(:email)", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    @Query(value = "select * from users u ", nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "select distinct r.role_name from userrole ur left join roles r  on ur.role_id = r.role_id where ur.user_id = :user_id limit 1 ORDER BY r.role_name ", nativeQuery = true)
    String getHighestRoleWithUser_Id(@Param("user_id") int user_id);
}
