package com.example.vfarmrdbackend.payload.project;

public class ProjectStatisticsResponse {
    private int total_project;
    private int total_project_running;
    private int total_project_canceled;

    public ProjectStatisticsResponse() {
    }

    public ProjectStatisticsResponse(int total_project, int total_project_running, int total_project_canceled) {
        this.total_project = total_project;
        this.total_project_running = total_project_running;
        this.total_project_canceled = total_project_canceled;
    }

    public int getTotal_project() {
        return total_project;
    }

    public void setTotal_project(int total_project) {
        this.total_project = total_project;
    }

    public int getTotal_project_running() {
        return total_project_running;
    }

    public void setTotal_project_running(int total_project_running) {
        this.total_project_running = total_project_running;
    }

    public int getTotal_project_canceled() {
        return total_project_canceled;
    }

    public void setTotal_project_canceled(int total_project_canceled) {
        this.total_project_canceled = total_project_canceled;
    }

}
