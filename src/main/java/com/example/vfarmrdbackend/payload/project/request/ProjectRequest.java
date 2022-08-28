package com.example.vfarmrdbackend.payload.project;

import java.util.Date;
import java.util.List;

public class ProjectRequest {
    private String project_name;
    private String client_id;
    private List<Integer> listUser_id;
    private String project_code;
    private String requirement;
    private Date start_date;
    private Date complete_date;
    private float estimated_weight;

    public ProjectRequest() {
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public List<Integer> getListUser_id() {
        return listUser_id;
    }

    public void setListUser_id(List<Integer> listUser_id) {
        this.listUser_id = listUser_id;
    }

}
