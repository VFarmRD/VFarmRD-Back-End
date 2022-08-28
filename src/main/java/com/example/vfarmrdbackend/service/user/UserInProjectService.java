package com.example.vfarmrdbackend.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.user.UserInProject;
import com.example.vfarmrdbackend.payload.user.response.UserInProjectResponse;
import com.example.vfarmrdbackend.repository.user.UserInProjectRepository;

@Service
public class UserInProjectService {
    @Autowired
    UserInProjectRepository userInProjectRepository;

    @Autowired
    UserService userService;

    public List<UserInProjectResponse> getAllUserInProjectWithProject_id(int project_id) {
        try {
            List<UserInProject> listUserInProject = userInProjectRepository
                    .getAllUserInProjectWithProject_id(project_id);
            List<UserInProjectResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listUserInProject.size(); i++) {
                listResponse.add(new UserInProjectResponse(
                        listUserInProject.get(i).getUser_id(),
                        userService.getUserInfo(listUserInProject.get(i).getUser_id()).getFullname(),
                        listUserInProject.get(i).getAssigned_date()));
            }
            return listResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Integer> getAllUser_idInProjectWithProject_id(int project_id) {
        try {
            List<UserInProject> listUserInProject = userInProjectRepository
                    .getAllUserInProjectWithProject_id(project_id);
            List<Integer> listUser_id = new ArrayList<>();
            for (int i = 0; i < listUserInProject.size(); i++) {
                listUser_id.add(listUserInProject.get(i).getUser_id());
            }
            return listUser_id;
        } catch (Exception e) {
            throw e;
        }
    }

    public UserInProject getUserInProjectWithUser_idAndProject_id(int user_id, int project_id) {
        try {
            return userInProjectRepository.getUserInProjectWithUser_idAndProject_id(user_id, project_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createUserInProject(int user_id, int project_id) {
        try {
            userInProjectRepository.save(new UserInProject(0,
                    user_id,
                    project_id,
                    new Date()));
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteUserInProject(int uip_id) {
        try {
            userInProjectRepository.delete(userInProjectRepository.getUserInProjectWithUip_id(uip_id));
        } catch (Exception e) {
            throw e;
        }
    }
}
