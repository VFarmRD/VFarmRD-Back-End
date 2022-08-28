package com.example.vfarmrdbackend.payload.task;

public class TaskStatisticsResponse {
    private int total_task;
    private int total_task_doing;
    private int total_task_done;
    private int total_task_overtime;

    public TaskStatisticsResponse() {
    }

    public TaskStatisticsResponse(int total_task, int total_task_doing, int total_task_done, int total_task_overtime) {
        this.total_task = total_task;
        this.total_task_doing = total_task_doing;
        this.total_task_done = total_task_done;
        this.total_task_overtime = total_task_overtime;
    }

    public int getTotal_task() {
        return total_task;
    }

    public void setTotal_task(int total_task) {
        this.total_task = total_task;
    }

    public int getTotal_task_doing() {
        return total_task_doing;
    }

    public void setTotal_task_doing(int total_task_doing) {
        this.total_task_doing = total_task_doing;
    }

    public int getTotal_task_done() {
        return total_task_done;
    }

    public void setTotal_task_done(int total_task_done) {
        this.total_task_done = total_task_done;
    }

    public int getTotal_task_overtime() {
        return total_task_overtime;
    }

    public void setTotal_task_overtime(int total_task_overtime) {
        this.total_task_overtime = total_task_overtime;
    }

}
