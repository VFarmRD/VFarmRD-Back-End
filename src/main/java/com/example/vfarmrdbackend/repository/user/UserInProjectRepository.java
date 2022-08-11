package com.example.vfarmrdbackend.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.user.UserInProject;

public interface UserInProjectRepository extends JpaRepository<UserInProject, Integer> {

    @Query(value = "select * from userinproject u where u.project_id = :project_id", nativeQuery = true)
    List<UserInProject> getAllUserInProjectWithProject_id(@Param("project_id") int project_id);

    @Query(value = "select * from userinproject u where u.uip_id = :uip_id", nativeQuery = true)
    UserInProject getUserInProjectWithUip_id(@Param("uip_id") int uip_id);

    @Query(value = "select * from userinproject u where u.user_id = :user_id and u.project_id = :project_id", nativeQuery = true)
    UserInProject getUserInProjectWithUser_idAndProject_id(@Param("user_id") int user_id,
            @Param("project_id") int project_id);
}
