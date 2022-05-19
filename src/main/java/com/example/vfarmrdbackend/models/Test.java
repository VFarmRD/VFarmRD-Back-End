package com.example.vfarmrdbackend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int file_id;
    private int user_id;
    private String file_name;
    private String file_path;
    private String file_type;
    private Date created_time;
    private Date modified_time;
    private boolean file_data;

    public Test() {
    }

    public Test(int file_id, int user_id, String file_name, String file_path, String file_type, Date created_time, Date modified_time, boolean file_data) {
        this.file_id = file_id;
        this.user_id = user_id;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_type = file_type;
        this.created_time = created_time;
        this.modified_time = modified_time;
        this.file_data = file_data;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
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

    public boolean isFile_data() {
        return file_data;
    }

    public void setFile_data(boolean file_data) {
        this.file_data = file_data;
    }
}
