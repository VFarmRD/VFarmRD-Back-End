package com.example.vfarmrdbackend.payload.test;

import com.example.vfarmrdbackend.payload.file.FileResponse;

public class TestGetResponse {
    private int test_id;
    private String test_content;
    private int user_id;
    private String user_name;
    private String user_role;
    private String test_expect;
    private boolean test_result;
    private String object_type;
    private FileResponse fileResponse;

    public TestGetResponse() {
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public String getTest_content() {
        return test_content;
    }

    public void setTest_content(String test_content) {
        this.test_content = test_content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getTest_expect() {
        return test_expect;
    }

    public void setTest_expect(String test_expect) {
        this.test_expect = test_expect;
    }

    public boolean isTest_result() {
        return test_result;
    }

    public void setTest_result(boolean test_result) {
        this.test_result = test_result;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public FileResponse getFileResponse() {
        return fileResponse;
    }

    public void setFileResponse(FileResponse fileResponse) {
        this.fileResponse = fileResponse;
    }

}
