package com.example.vfarmrdbackend.payload;

public class MessageResponse {
    private String message_name;
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String message_name, String message) {
        this.message_name = message_name;
        this.message = message;
    }

    public String getMessage_name() {
        return message_name;
    }

    public void setMessage_name(String message_name) {
        this.message_name = message_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
