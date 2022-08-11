package com.example.vfarmrdbackend.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userinproject")
public class UserInProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uip_id;
    private int user_id;
    private int project_id;
    private Date assigned_date;

    public UserInProject() {
    }

    public UserInProject(int uip_id, int user_id, int project_id, Date assigned_date) {
        this.uip_id = uip_id;
        this.user_id = user_id;
        this.project_id = project_id;
        this.assigned_date = assigned_date;
    }

    public int getUip_id() {
        return uip_id;
    }

    public void setUip_id(int uip_id) {
        this.uip_id = uip_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Date getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(Date assigned_date) {
        this.assigned_date = assigned_date;
    }

}
