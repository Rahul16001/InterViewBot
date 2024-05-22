package com.example.interviewrgpt.Model;

import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("role")
    String role;

    @SerializedName("content")
    String content;

    public MessageModel(String role, String content)
    {
        this.content = content;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
