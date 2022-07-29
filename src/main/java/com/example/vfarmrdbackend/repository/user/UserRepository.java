package com.example.vfarmrdbackend.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

        @Query(value = "select * from users u where lower(u.user_name) like lower(:user_name)", nativeQuery = true)
        User getUserByUser_name(@Param("user_name") String user_name);

        @Query(value = "select * from users u where u.user_id = :user_id", nativeQuery = true)
        User getUserByUser_id(@Param("user_id") int user_id);

        @Query(value = "select * from users u where lower(u.email) like lower(:email)", nativeQuery = true)
        User getUserByEmail(@Param("email") String email);

        @Query(value = "select * from users u " +
                        "where lower(u.user_name) like lower(:user_name) and " +
                        "lower(u.email) like lower(:email) and " +
                        "lower(u.fullname) like lower(:fullname) and " +
                        "lower(u.phone) like lower(:phone) and " +
                        "lower(u.role_name) like lower(:role_name) and " +
                        "u.user_status = :user_status ", nativeQuery = true)
        Page<User> findUserByFields(@Param("user_name") String user_name,
                        @Param("email") String email,
                        @Param("fullname") String fullname,
                        @Param("phone") String phone,
                        @Param("role_name") String role_name,
                        @Param("user_status") boolean user_status,
                        Pageable pageable);

        @Query(value = "select * from users", nativeQuery = true)
        Page<User> findAllUsers(Pageable pageable);

}
