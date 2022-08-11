package com.example.vfarmrdbackend.payload.project;

import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.payload.user.UserInProjectResponse;

public class ProjectGetResponse {
    private int project_id;
    private String project_name;
    private String client_id;
    private int created_user_id;
    private String created_user_name;
    private List<UserInProjectResponse> listUserInProject;
    private String project_code;
    private String requirement;
    private Date created_time;
    private Date modified_time;
    private Date start_date;
    private Date complete_date;
    private float estimated_weight;
    private String project_status;

    public ProjectGetResponse() {
    }

    public ProjectGetResponse(int project_id, String project_name, String client_id, int created_user_id,
            String created_user_name, List<UserInProjectResponse> listUserInProject, String project_code,
            String requirement, Date created_time, Date modified_time, Date start_date, Date complete_date,
            float estimated_weight, String project_status) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.client_id = client_id;
        this.created_user_id = created_user_id;
        this.created_user_name = created_user_name;
        this.listUserInProject = listUserInProject;
        this.project_code = project_code;
        this.requirement = requirement;
        this.created_time = created_time;
        this.modified_time = modified_time;
        this.start_date = start_date;
        this.complete_date = complete_date;
        this.estimated_weight = estimated_weight;
        this.project_status = project_status;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

    public Date getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(Date complete_date) {
        this.complete_date = complete_date;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public int getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(int created_user_id) {
        this.created_user_id = created_user_id;
    }

    public String getCreated_user_name() {
        return created_user_name;
    }

    public void setCreated_user_name(String created_user_name) {
        this.created_user_name = created_user_name;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public float getEstimated_weight() {
        return estimated_weight;
    }

    public void setEstimated_weight(float estimated_weight) {
        this.estimated_weight = estimated_weight;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public List<UserInProjectResponse> getListUserInProject() {
        return listUserInProject;
    }

    public void setListUserInProject(List<UserInProjectResponse> listUserInProject) {
        this.listUserInProject = listUserInProject;
    }

}
