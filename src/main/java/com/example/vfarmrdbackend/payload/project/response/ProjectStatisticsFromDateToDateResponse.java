package com.example.vfarmrdbackend.payload.project;

public class ProjectStatisticsFromDateToDateResponse {
    private String date;
    private int total_project;
    private int total_project_running;
    private int total_project_canceled;
    private int total_project_have_product;

    public ProjectStatisticsFromDateToDateResponse() {
    }

    public ProjectStatisticsFromDateToDateResponse(String date, int total_project, int total_project_running,
            int total_project_canceled, int total_project_have_product) {
        this.date = date;
        this.total_project = total_project;
        this.total_project_running = total_project_running;
        this.total_project_canceled = total_project_canceled;
        this.total_project_have_product = total_project_have_product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getTotal_project_have_product() {
        return total_project_have_product;
    }

    public void setTotal_project_have_product(int total_project_have_product) {
        this.total_project_have_product = total_project_have_product;
    }

}
