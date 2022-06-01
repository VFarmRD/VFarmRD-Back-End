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

}
